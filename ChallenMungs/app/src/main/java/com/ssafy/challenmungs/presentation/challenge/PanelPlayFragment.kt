package com.ssafy.challenmungs.presentation.challenge

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentPanelPlayBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class PanelPlayFragment : BaseFragment<FragmentPanelPlayBinding>(R.layout.fragment_panel_play) {

    override fun initView() {
        val toolbar = binding.toolbar
        toolbar.tvTitle.text = "임의의 챌린지 제목입니다."
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.addMarker(MarkerOptions().position(defaultPosition).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultPosition))
    }

    companion object {
        private val defaultPosition = LatLng(36.107102, 128.416558)
    }
}