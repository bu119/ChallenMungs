package com.ssafy.challenmungs.presentation.information

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
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
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information),
OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var powered = false
    private lateinit var placeListCards: String
    val regionList = mutableListOf("경상북도")
    val chipToggle = Array(4) { false }
    lateinit var myLatLng: LatLng

    override fun initView() {

        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment
        var showRegionBox = false

        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.llSelectRegion.setOnClickListener {
            val cl = binding.clRegionSelectBox
            cl.visibility = View.VISIBLE
            cl.post {
                val height = cl.height
                var anim: TranslateAnimation
                if (showRegionBox == false) {
                    anim = TranslateAnimation(0f, 0f, height.toFloat(), 0f)
                }
                else {
                    anim = TranslateAnimation(0f, 0f, 0f, height.toFloat())
                }
                showRegionBox = !showRegionBox
                anim.fillAfter = true
                anim.duration = 250

                cl.startAnimation(anim)
            }
        }

        binding.ivRegionBoxClose.setOnClickListener {
            if (showRegionBox) {
                val cl = binding.clRegionSelectBox
                val height = cl.height
                var anim: TranslateAnimation
                anim = TranslateAnimation(0f, 0f, 0f, height.toFloat())
                showRegionBox = !showRegionBox
                anim.fillAfter = true
                anim.duration = 250
                cl.startAnimation(anim)
            }
        }
        binding.ivPlaceListLoadButton.setOnClickListener {
            navigate(InformationFragmentDirections.actionToInformationPlaceListFragment(placeListCards))
        }

        val list = mutableListOf(binding.tvRegionChunchungbukdo, binding.tvRegionDaejeon, binding.tvRegionDaegu, binding.tvRegionChunchungnamdo, binding.tvRegionJeonrabukdo, binding.tvRegionIncheon, binding.tvRegionJeonranamdo, binding.tvRegionKangwondo, binding.tvRegionKwanju, binding.tvRegionKyungsangbukdo, binding.tvRegionKyungsangnamdo, binding.tvRegionKyunkido, binding.tvRegionPusan, binding.tvRegionSeoul)

        for (i in list) {
            i.setOnClickListener {
                if(!regionList.contains(i.text.toString())) {
                    i.setBackgroundResource(R.drawable.bg_rect_golden_glow_radius100)
                    regionList.add(i.text.toString())
                }
                else {
                    i.setBackgroundResource(R.drawable.bg_rect_white_radius100_with_stroke_goldenglow)
                    regionList.remove(i.text.toString())
                }
                googleMap.clear()
                GetPlaceList("http://j8d2101.p.ssafy.io:8080/place/lsit", null, null).execute()
            }
        }
        binding.tvSearchText.setOnEditorActionListener{ _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                Toast.makeText(requireContext(), binding.tvSearchText.text, Toast.LENGTH_SHORT).show()

                GetPlaceList("http://j8d2101.p.ssafy.io:8080/place/lsit", binding.tvSearchText.text.toString(), null).execute()
                true
            } else {
                false
            }
        }

        var chipList = listOf(binding.llHospitalChip, binding.llPharmacyChip, binding.llCafeChip, binding.llMealChip)
        val chipListValue = arrayOf("동물병원", "동물약국", "카페", "식당")

        for (i in chipList) {
            i.setOnClickListener {
                if (!chipToggle[chipList.indexOf(i)]) {
                    i.setBackgroundResource(R.drawable.bg_rect_golden_glow_radius100)
                    googleMap.clear()
                    GetPlaceList("http://j8d2101.p.ssafy.io:8080/place/lsit", null, chipListValue[chipList.indexOf(i)]).execute()
                    for (j in 0 until chipList.size) {
                        if (chipToggle[j]) {
                            chipToggle[j] = false
                            chipList.get(j).setBackgroundResource(R.drawable.bg_rect_white_radius100)
                        }
                    }
                    chipToggle[chipList.indexOf(i)] = true;
                } else {
                    for (j in 0 until chipList.size) {
                        if (chipToggle[j]) {
                            chipToggle[j] = false
                            chipList.get(j).setBackgroundResource(R.drawable.bg_rect_white_radius100)
                        }
                    }
                    googleMap.clear()
                    GetPlaceList("http://j8d2101.p.ssafy.io:8080/place/lsit", null, null).execute()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (powered == false) {
            this.googleMap = googleMap
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        myLatLng = LatLng(it.latitude, it.longitude)

                        // 지도 위치와 축척 조정하기
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 11f))

                        GetPlaceList("http://j8d2101.p.ssafy.io:8080/place/lsit", null, null).execute()
                    }
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            powered = !powered
        }
    }

    inner class GetPlaceList(
        private val url: String,
        private val searchValue: String?,
        private val type: String?
    ) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val client = OkHttpClient()

            val citiesStr = regionList.joinToString(",")
            val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
            urlBuilder?.addQueryParameter("cities", citiesStr)
            if (searchValue != null) urlBuilder?.addQueryParameter("name", searchValue)
            if (type != null) urlBuilder?.addQueryParameter("type", type)

            val request = Request.Builder()
                .url(urlBuilder?.build().toString())
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            response.close()

            placeListCards = JSONObject(responseBody).getJSONArray("content").toString() ?: ""
            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            val jsonArray = JSONObject(result).getJSONArray("content")
            var newCameraLat = 0.0
            var newCameraLng = 0.0
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val loc = LatLng(jsonObj.get("lat").toString().toDouble(), jsonObj.get("lng").toString().toDouble())
                if (searchValue == null) {
                    val markerOptions = MarkerOptions()
                        .position(loc)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_pin))
                        .title(jsonObj.get("name").toString())
                        .snippet("주소 : ${jsonObj.get("address").toString()}")

                    googleMap.addMarker(markerOptions)
                } else {
                    val markerOptions = MarkerOptions()
                        .position(loc).zIndex(5000f)
                        .title(jsonObj.get("name").toString())
                        .snippet("주소 : ${jsonObj.get("address").toString()}")

                    newCameraLat = loc.latitude
                    newCameraLng = loc.longitude
                    googleMap.addMarker(markerOptions)
                }
            }
            if (searchValue != null && jsonArray.length() > 0) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(newCameraLat, newCameraLng), googleMap.cameraPosition.zoom))
            }
        }
    }
}