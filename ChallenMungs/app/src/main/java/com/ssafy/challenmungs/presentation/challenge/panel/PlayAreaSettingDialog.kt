package com.ssafy.challenmungs.presentation.challenge.panel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.DialogSizeHelper.dialogFragmentResize
import com.ssafy.challenmungs.common.util.MapHelper
import com.ssafy.challenmungs.common.util.MapHelper.defaultPosition
import com.ssafy.challenmungs.databinding.DialogPlayAreaSettingBinding

class PlayAreaSettingDialog(private val playAreaSettingInterface: PlayAreaSettingInterface) :
    DialogFragment(), OnMapReadyCallback {

    private lateinit var binding: DialogPlayAreaSettingBinding
    private var myMarker: Marker? = null
    private var rect: Polygon? = null

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
        binding.btnReset.setOnClickListener {
            setMarker(map, defaultPosition)
            setRect(map, R.color.trans30_golden_poppy, defaultPosition)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_ZOOM))
        }

        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_ZOOM))
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.apply {
                isZoomControlsEnabled = false
                isMapToolbarEnabled = false
                isTiltGesturesEnabled = false
            }
            setMarker(map, defaultPosition)
            setRect(map, R.color.trans30_golden_poppy, defaultPosition)
            setOnMapClickListener { touchPosition ->
                setMarker(this, touchPosition)
                setRect(this, R.color.trans30_golden_poppy, touchPosition)
                animateCamera(CameraUpdateFactory.newLatLng(touchPosition))
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
            playAreaSettingInterface.setArea()
        }
    }

    private fun setMarker(googleMap: GoogleMap, position: LatLng) {
        val drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_red_marker) as VectorDrawable
        val bitmap = drawable.toBitmap()

        myMarker?.remove()
        myMarker = googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )
    }

    private fun setRect(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        rect?.remove()
        val rectOptions = PolygonOptions().apply {
            addAll(MapHelper.createRectangle(center, DISTANCE))
            fillColor(ContextCompat.getColor(requireContext(), fillColorArgb))
            strokeWidth(0f)
        }
        rect = googleMap.addPolygon(rectOptions)
    }

    companion object {
        private const val DEFAULT_ZOOM = 13f
        private const val DISTANCE = 0.15
    }
}