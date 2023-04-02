package com.ssafy.challenmungs.presentation.information

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentInformationBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information),
OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)

                    // 지도 위치와 축척 조정하기
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))

                    val locations = listOf(
                        LatLng(37.5666791, 126.9782914), // 서울역
                        LatLng(37.4979467, 127.0276368), // 강남역
                        LatLng(37.554722, 126.970833)   // 광화문역
                    )

                    for (location in locations) {
                        // 마커 추가하기
                        val markerOptions = MarkerOptions()
                            .position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_pin))
                        googleMap.addMarker(markerOptions)
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

}