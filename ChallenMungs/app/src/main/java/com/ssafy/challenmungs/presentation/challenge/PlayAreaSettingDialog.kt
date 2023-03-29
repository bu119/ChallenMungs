package com.ssafy.challenmungs.presentation.challenge

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.Map
import com.ssafy.challenmungs.common.util.Map.defaultPosition
import com.ssafy.challenmungs.databinding.DialogPlayAreaSettingBinding

class PlayAreaSettingDialog(
    context: Context,
    private val playAreaSettingInterface: PlayAreaSettingInterface
) : Dialog(context), OnMapReadyCallback {

    private lateinit var binding: DialogPlayAreaSettingBinding
    private var myMarker: Marker? = null
    private var rect: Polygon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initMapView(savedInstanceState)
        initListener()
    }

    override fun onMapReady(map: GoogleMap) {
        binding.btnReset.setOnClickListener {
            setMarker(map, defaultPosition)
        }
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 15f))
            mapType = GoogleMap.MAP_TYPE_NORMAL
            setOnMapClickListener { touchPosition ->
                setMarker(this, touchPosition)
                setRect(this, R.color.trans30_golden_poppy, touchPosition)
            }
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        binding.fcvGoogleMap.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PlayAreaSettingDialog)
        }
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
        myMarker?.remove()
        myMarker = googleMap.addMarker(
            MarkerOptions()
                .draggable(true)
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .anchor(0.5f, 0.5f)
        )
    }

    private fun setRect(googleMap: GoogleMap, fillColorArgb: Int, center: LatLng) {
        rect?.remove()
        val rectOptions = PolygonOptions().apply {
            addAll(Map.createRectangle(center, 0.1))
            fillColor(ContextCompat.getColor(context, fillColorArgb))
            strokeWidth(0f)
        }
        rect = googleMap.addPolygon(rectOptions)
    }
}