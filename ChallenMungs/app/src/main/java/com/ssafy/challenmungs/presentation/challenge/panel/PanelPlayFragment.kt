package com.ssafy.challenmungs.presentation.challenge.panel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.MapHelper.DISTANCE
import com.ssafy.challenmungs.common.util.MapHelper.checkLocationServicesStatus
import com.ssafy.challenmungs.common.util.MapHelper.createRectangle
import com.ssafy.challenmungs.common.util.MapHelper.defaultPosition
import com.ssafy.challenmungs.common.util.MapHelper.locationRequest
import com.ssafy.challenmungs.common.util.MapHelper.replaceDrawableMarker
import com.ssafy.challenmungs.common.util.PermissionHelper
import com.ssafy.challenmungs.common.util.px
import com.ssafy.challenmungs.databinding.FragmentPanelPlayBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanelPlayFragment : BaseFragment<FragmentPanelPlayBinding>(R.layout.fragment_panel_play),
    OnMapReadyCallback {

    private val panelPlayViewModel by activityViewModels<PanelPlayViewModel>()
    private lateinit var profileImg: String
    private val panels = mutableListOf<Polygon>()
    private var myMarker: Marker? = null
    private lateinit var map: GoogleMap
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private lateinit var currentPosition: LatLng

    //위치정보 요청시 호출
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                currentPosition = LatLng(location.latitude, location.longitude)
                Log.d(TAG, "onLocationResult: 위도: ${location.latitude}, 경도: ${location.longitude}")

                createMyMarker(map, profileImg)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, DEFAULT_ZOOM))
            }
        }
    }

    override fun initView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        profileImg = getString(R.string.default_profile_url)

        binding.btnWalk.setOnClickListener {
            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setMyLocation()
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_ZOOM))
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
        }
    }

    // 내 위치 설정
    @SuppressLint("MissingPermission")
    private fun setMyLocation() {
        // 위치서비스 활성화 여부 check
        if (!checkLocationServicesStatus(activity)) {
            Toast.makeText(
                context,
                "위치 서비스가 꺼져 있어, 현재 위치를 확인할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (PermissionHelper.hasLocationPermission(requireContext())) {
                mFusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.location_permission_warning_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setTile(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        val rectOptions = PolygonOptions().apply {
            addAll(createRectangle(center, DISTANCE))
            fillColor(ContextCompat.getColor(requireContext(), fillColorArgb))
            strokeWidth(1f)
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
                            .position(currentPosition)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            .anchor(0.5f, 0.5f)
                    )
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    myMarker?.remove()

                    myMarker = replaceDrawableMarker(googleMap, placeholder, currentPosition)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    myMarker?.remove()
                    myMarker = replaceDrawableMarker(googleMap, errorDrawable, currentPosition)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    myMarker?.remove()
                    myMarker = replaceDrawableMarker(googleMap, placeholder, currentPosition)
                }
            })
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (PermissionHelper.hasLocationPermission(requireContext())) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val TAG = "PanelPlayFragment_챌린멍스"
        private const val DEFAULT_ZOOM = 15f
    }
}