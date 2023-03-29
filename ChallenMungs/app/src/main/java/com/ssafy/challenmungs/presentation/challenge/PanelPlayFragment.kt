package com.ssafy.challenmungs.presentation.challenge

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.Map.createRectangle
import com.ssafy.challenmungs.common.util.Map.defaultPosition
import com.ssafy.challenmungs.common.util.px
import com.ssafy.challenmungs.databinding.FragmentPanelPlayBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanelPlayFragment : BaseFragment<FragmentPanelPlayBinding>(R.layout.fragment_panel_play),
    OnMapReadyCallback {

    private val panels = mutableListOf<Polygon>()
    private var myMarker: Marker? = null

    override fun initView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 15f))
            // 세로
            for (i in -10..10) {
                for (j in -10..10) {
                    setTile(
                        this, R.color.trans20_pink_swan, LatLng(
                            defaultPosition.latitude + i * DISTANCE * 2,
                            defaultPosition.longitude + j * DISTANCE * 2,
                        )
                    )
                }
            }

            val profileImg = getString(R.string.default_profile_url)
            createMyMarker(this, profileImg)
        }
    }

    private fun setTile(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        val rectOptions = PolygonOptions().apply {
            addAll(createRectangle(center, DISTANCE))
            fillColor(ContextCompat.getColor(requireContext(), fillColorArgb))
            strokeWidth(0f)
        }
        panels.add(googleMap.addPolygon(rectOptions))
    }

    private fun createMyMarker(googleMap: GoogleMap, profileImg: String) {
        Glide.with(this@PanelPlayFragment)
            .asBitmap()
            .load(profileImg)
            .placeholder(R.drawable.ic_profile_default)
            .error(R.drawable.ic_profile_default)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val pixels = 30.px(requireContext())
                    val bitmap =
                        Bitmap.createScaledBitmap(resource, pixels, pixels, true)
                    myMarker?.remove()
                    myMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(defaultPosition)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            .anchor(0.5f, 0.5f)
                    )
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    replaceDrawableMarker(googleMap, placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    replaceDrawableMarker(googleMap, errorDrawable)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    replaceDrawableMarker(googleMap, placeholder)
                }
            })
    }

    fun replaceDrawableMarker(googleMap: GoogleMap, drawable: Drawable?) {
        val bitmapDrawable = drawable as VectorDrawable
        val bitmap = bitmapDrawable.toBitmap()
        myMarker?.remove()
        myMarker = googleMap.addMarker(
            MarkerOptions()
                .position(defaultPosition)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(bitmap)
                )
                .anchor(0.5f, 0.5f)
        )
    }

    companion object {
        private const val DISTANCE = 0.001
    }
}