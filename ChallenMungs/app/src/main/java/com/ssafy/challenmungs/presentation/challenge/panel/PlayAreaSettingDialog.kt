package com.ssafy.challenmungs.presentation.challenge.panel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.DialogSizeHelper.dialogFragmentResize
import com.ssafy.challenmungs.common.util.MapHelper
import com.ssafy.challenmungs.common.util.MapHelper.DEFAULT_SETTING_ZOOM
import com.ssafy.challenmungs.common.util.MapHelper.DISTANCE
import com.ssafy.challenmungs.common.util.MapHelper.currentLocationRequest
import com.ssafy.challenmungs.common.util.MapHelper.defaultPosition
import com.ssafy.challenmungs.common.util.MapHelper.getLastKnownLocation
import com.ssafy.challenmungs.common.util.PermissionHelper
import com.ssafy.challenmungs.databinding.DialogPlayAreaSettingBinding

class PlayAreaSettingDialog(
    context: Context,
    private val playAreaSettingInterface: PlayAreaSettingInterface
) :
    DialogFragment(), OnMapReadyCallback {

    private lateinit var binding: DialogPlayAreaSettingBinding
    private lateinit var googleMap: GoogleMap
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(mContext)
    }
    private var mContext: Context
    private var myMarker: Marker? = null
    private var rect: Polygon? = null
    private var currentLocation: LatLng = defaultPosition
    private var submitLocation: LatLng = currentLocation

    init {
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPlayAreaSettingBinding.inflate(inflater, container, false)

        initSetting()
        initMapView()
        initListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.8f)
    }

    private fun initSetting() {
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = true
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        mapSetting(currentLocation)
        setMyLocation()
    }

    private fun mapSetting(location: LatLng) {
        with(googleMap) {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.apply {
                isZoomControlsEnabled = false
                isMapToolbarEnabled = false
                isTiltGesturesEnabled = false
            }

            moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_SETTING_ZOOM))
            setMarker(googleMap, location)
            setRect(googleMap, R.color.trans30_golden_poppy, location)
            setOnMapClickListener { touchPosition ->
                setMarker(this, touchPosition)
                setRect(
                    this,
                    R.color.trans30_golden_poppy,
                    touchPosition
                )
                animateCamera(
                    CameraUpdateFactory.newLatLng(
                        touchPosition
                    )
                )

                submitLocation = touchPosition
            }

            binding.btnReset.setOnClickListener {
                setMyLocation()
            }
        }
    }

    private fun initMapView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            playAreaSettingInterface.setArea(submitLocation)
            dismiss()
        }
    }

    private fun setMarker(googleMap: GoogleMap, position: LatLng) {
        val drawable =
            ContextCompat.getDrawable(mContext, R.drawable.ic_red_marker) as VectorDrawable
        val bitmap = drawable.toBitmap()

        myMarker?.remove()
        myMarker = googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )
    }

    private fun setRect(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        val rectOptions = PolygonOptions().apply {
            addAll(MapHelper.createRectangle(center, DISTANCE))
            fillColor(ContextCompat.getColor(mContext, fillColorArgb))
            strokeWidth(0f)
        }

        rect?.remove()
        rect = googleMap.addPolygon(rectOptions)
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocation() {
        // 위치서비스 활성화 여부 check
        if (!MapHelper.checkLocationServicesStatus(activity)) {
            Toast.makeText(
                context,
                getString(R.string.content_gps_off_warning),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (PermissionHelper.hasLocationPermission(mContext)) {
                getLastKnownLocation(activity)?.let {
                    currentLocation = it
                }
                getCurrentLocation()
            } else {
                Toast.makeText(
                    mContext,
                    getString(R.string.content_get_my_location_warning_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        mFusedLocationClient.getCurrentLocation(currentLocationRequest, null).addOnSuccessListener {
            if (it != null) {
                val position = LatLng(it.latitude, it.longitude)
                currentLocation = position
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        position,
                        DEFAULT_SETTING_ZOOM
                    )
                )

                setMarker(googleMap, currentLocation)
                setRect(googleMap, R.color.trans30_golden_poppy, currentLocation)
            }
        }
    }
}