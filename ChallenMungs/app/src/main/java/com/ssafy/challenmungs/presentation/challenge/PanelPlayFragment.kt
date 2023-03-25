package com.ssafy.challenmungs.presentation.challenge

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.ssafy.challenmungs.databinding.FragmentPanelPlayBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment


class PanelPlayFragment : BaseFragment<FragmentPanelPlayBinding>(R.layout.fragment_panel_play),
    OnMapReadyCallback {

    private val panels = mutableListOf<Polygon>()
    private var myMarker: Marker? = null

    override fun initView() {
        val toolbar = binding.toolbar
        toolbar.tvTitle.text = "임의의 챌린지 제목입니다."
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

            val profileImg =
                "https://kr.object.ncloudstorage.com/challenmungs-storage/user/e39faf10-48c3-4deb-99dc-0d12df102499ic_profile.png"
            createMyMarker(this, profileImg)
        }
    }

    private fun setTile(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        val rectOptions = PolygonOptions().apply {
            addAll(createRectangle(center, DISTANCE, DISTANCE))
            fillColor(ContextCompat.getColor(requireContext(), fillColorArgb))
            strokeWidth(1f)
        }
        panels.add(googleMap.addPolygon(rectOptions))
    }

    private fun createRectangle(
        center: LatLng,
        halfWidth: Double,
        halfHeight: Double
    ): List<LatLng> {
        return listOf(
            LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
            LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
            LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
            LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
            LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
        )
    }

    private fun createMyMarker(googleMap: GoogleMap, profileImg: String) {
        Glide.with(this@PanelPlayFragment)
            .asBitmap()
            .load(profileImg)
            .placeholder(R.drawable.ic_dog_calendar)
            .error(R.drawable.ic_treasure_close)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val scale = context!!.resources.displayMetrics.density
                    val pixels = (30 * scale + 0.5f).toInt()
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
                    val bitmapDrawable = placeholder as VectorDrawable
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

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    val bitmapDrawable = errorDrawable as VectorDrawable
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

                override fun onLoadCleared(placeholder: Drawable?) {
                    val bitmapDrawable = placeholder as BitmapDrawable
                    val bitmap = bitmapDrawable.bitmap
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
            })
    }

    companion object {
        private const val DISTANCE = 0.001
        private val defaultPosition = LatLng(36.107102, 128.416558)
    }
}