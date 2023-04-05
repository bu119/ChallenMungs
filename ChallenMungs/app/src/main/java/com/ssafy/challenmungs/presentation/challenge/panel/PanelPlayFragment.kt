package com.ssafy.challenmungs.presentation.challenge.panel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.*
import com.ssafy.challenmungs.common.util.MapHelper.DEFAULT_ZOOM
import com.ssafy.challenmungs.common.util.MapHelper.checkLocationServicesStatus
import com.ssafy.challenmungs.common.util.MapHelper.defaultPosition
import com.ssafy.challenmungs.common.util.MapHelper.getColor
import com.ssafy.challenmungs.common.util.MapHelper.getLastKnownLocation
import com.ssafy.challenmungs.common.util.MapHelper.locationRequest
import com.ssafy.challenmungs.common.util.MapHelper.replaceDrawableMarker
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.Location
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.MessageListener
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.WebSocketManager
import com.ssafy.challenmungs.databinding.FragmentPanelPlayBinding
import com.ssafy.challenmungs.domain.entity.challenge.PanelRevertResponse
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class PanelPlayFragment : BaseFragment<FragmentPanelPlayBinding>(R.layout.fragment_panel_play),
    OnMapReadyCallback, MessageListener {

    @Inject
    lateinit var webSocketManager: WebSocketManager

    private lateinit var map: GoogleMap
    private val panelPlayViewModel by activityViewModels<PanelPlayViewModel>()
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private val panels = ArrayList<ArrayList<Polygon?>>(LENGTH).apply {
        for (i in 0..LENGTH) {
            add(ArrayList<Polygon?>(LENGTH).apply {
                for (j in 0..LENGTH) {
                    add(null)
                }
            })
        }
    }
    private var myMarker: Marker? = null
    private var roomNum: Long = 0L
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations

            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                val position = LatLng(location.latitude, location.longitude)
                panelPlayViewModel.setCurrentPosition(position)
                map.animateCamera(CameraUpdateFactory.newLatLng(position))
            }
        }
    }

    override fun initView() {
        val accessToken = ApplicationClass.preferences.accessToken

        getSelectedId()?.let {
            roomNum = it
        }

        if (accessToken != null) {
            panelPlayViewModel.setUserId(extractIdFromJWT(accessToken))
            lifecycleScope.launch {
                panelPlayViewModel.getPanelInfo(roomNum)
            }
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        setBind()
        initListener()
    }

    private fun setBind() {
        val observer = Observer<Pair<Int, Int>> {
            binding.tvMyRank.text =
                String.format(
                    resources.getString(R.string.content_panel_rank),
                    it.first,
                    it.second
                )
        }

        panelPlayViewModel.challengeInfo.observe(viewLifecycleOwner) {
            binding.title = it?.title
        }
        panelPlayViewModel.myRank.observe(viewLifecycleOwner, observer)
    }

    private fun initListener() {
        binding.btnWalk.setOnClickListener {
            startWalking()
            revertPanel()
        }

        binding.toolbar.tvInfo.setOnClickListener {
            navigate(PanelPlayFragmentDirections.actionToPanelInfoFragment())
        }

        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }

    private fun startWalking() {
        if (!webSocketManager.isConnect()) {
            val accessToken = ApplicationClass.preferences.accessToken

            if (accessToken != null) {
                webSocketManager.connect()
            }

            binding.btnWalk.text = getString(R.string.content_walk_end)
        } else {
            binding.btnWalk.text = getString(R.string.content_walk_start)
            webSocketManager.disconnect()
        }
    }

    private fun revertPanel() {
        if (webSocketManager.isConnect()) {
            panelPlayViewModel.userId.value?.let { userId ->
                panelPlayViewModel.currentPosition.observe(
                    viewLifecycleOwner
                ) { currentPosition ->
                    if (currentPosition != null) {
                        webSocketManager.revertPanel(
                            currentPosition.latitude,
                            currentPosition.longitude,
                            roomNum,
                            userId
                        )
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        panelPlayViewModel.currentPosition.observe(viewLifecycleOwner) {
            createMyMarker(map, panelPlayViewModel.myProfileImg.value, it)
        }
        setMyLocation()

        with(map) {
            panelPlayViewModel.center.observe(viewLifecycleOwner) { center ->
                if (center != null) {
                    val coordinate = panelPlayViewModel.challengeInfo.value?.mapCoordinate
                    moveCamera(CameraUpdateFactory.newLatLngZoom(center, DEFAULT_ZOOM))

                    if (coordinate != null)
                        panelPlayViewModel.mapInfo.observe(viewLifecycleOwner) {
                            it.forEachIndexed { i, arr ->
                                arr.forEachIndexed { j, value ->
                                    setTile(
                                        i, j,
                                        this, getColor(requireContext(), value), coordinate[i][j]
                                    )
                                }
                            }
                        }
                } else {
                    moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_ZOOM))
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
                getString(R.string.content_gps_off_warning),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (PermissionHelper.hasLocationPermission(requireContext())) {
                panelPlayViewModel.setCurrentPosition(getLastKnownLocation(activity))
                mFusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.content_location_permission_warning_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setTile(
        i: Int,
        j: Int,
        googleMap: GoogleMap,
        fillColorArgb: Int,
        coordinate: ArrayList<Location>
    ) {
        val mapping = coordinate.mapIndexed { index, latLng ->
            when (index) {
                2 -> LatLng(coordinate[3].lat, coordinate[3].lng)
                3 -> LatLng(coordinate[2].lat, coordinate[2].lng)
                else -> LatLng(latLng.lat, latLng.lng)
            }
        }
        val rectOptions = PolygonOptions().apply {
            addAll(mapping)
            fillColor(fillColorArgb)
            strokeWidth(0f)
        }

        panels[i][j]?.remove()
        panels[i][j] = googleMap.addPolygon(rectOptions)
    }

    private fun createMyMarker(googleMap: GoogleMap, profileImg: String?, position: LatLng?) {
        if (position != null) {
            Glide.with(this@PanelPlayFragment)
                .load(profileImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload()

            Glide.with(this@PanelPlayFragment)
                .asBitmap()
                .thumbnail()
                .load(profileImg)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(50, 50)
                .optionalCircleCrop()
                .placeholder(R.drawable.ic_profile_default)
                .error(R.drawable.ic_profile_default)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val pixels = 40.px(requireContext())
                        val bitmap =
                            Bitmap.createScaledBitmap(resource, pixels, pixels, true)

                        myMarker?.remove()
                        myMarker = googleMap.addMarker(
                            MarkerOptions()
                                .position(position)
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                .anchor(0.5f, 0.5f)
                        )
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        myMarker?.remove()
                        myMarker = replaceDrawableMarker(
                            googleMap,
                            placeholder,
                            position,
                            requireContext()
                        )
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        myMarker?.remove()
                        myMarker = replaceDrawableMarker(
                            googleMap,
                            errorDrawable,
                            position,
                            requireContext()
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (PermissionHelper.hasLocationPermission(requireContext())) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(webSocketManager)
        webSocketManager.init("${Constants.WEB_SOCKET}/panelSocket", this)
    }

    override fun onPause() {
        super.onPause()
        lifecycle.removeObserver(webSocketManager)
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onConnectSuccess() {
        panelPlayViewModel.userId.value?.let {
            webSocketManager.startWalking(roomNum, it)
        }
    }

    override fun onMessage(text: String?) {
        val json = text?.let { JSONObject(it) }

        when (json?.getString("code")) {
            "access" -> {
                val valueJson = json.getJSONObject("value")
                val mapInfoJson = valueJson.getJSONArray("mapInfo")
                val mapInfoList = ArrayList<ArrayList<Int>>()

                for (i in 0 until mapInfoJson.length()) {
                    val innerJsonArray = mapInfoJson.getJSONArray(i)
                    val innerList = ArrayList<Int>()

                    for (j in 0 until innerJsonArray.length()) {
                        innerList.add(innerJsonArray.getInt(j))
                    }
                    mapInfoList.add(innerList)
                }

                activity?.runOnUiThread {
                    panelPlayViewModel.setMapInfo(mapInfoList)
                }
            }
            "signaling" -> {
                val gson = Gson()
                val value =
                    gson.fromJson(JSONObject(text).toString(), PanelRevertResponse::class.java)
                val rankInfoList = ArrayList<RankDetail>()
                val c = value.value.indexC
                val r = value.value.indexR

                if (c >= 0 && r >= 0 && c < LENGTH && r < LENGTH) {
                    for (i in 0 until value.value.rankInfo.size) {
                        val innerRank = value.value.rankInfo[i]
                        val crown = when (innerRank.indiRank) {
                            1 -> R.drawable.ic_gold_crown
                            2 -> R.drawable.ic_silver_crown
                            3 -> R.drawable.ic_bronze_crown
                            else -> null
                        }

                        innerRank.crown = crown
                        rankInfoList.add(innerRank)
                    }

                    Handler(Looper.getMainLooper()).post {
                        panelPlayViewModel.rankInfo.observe(viewLifecycleOwner) { rankInfoList ->
                            val panel = panels[r][c]
                            panel?.let {
                                it.fillColor = getColor(requireContext(), value.value.teamId)
                            }
                        }
                        panelPlayViewModel.setRankInfo(rankInfoList)
                    }
                }
            }
        }
    }

    override fun onConnectFailed() {}

    override fun onClose() {}

    companion object {
        private const val LENGTH = 20
    }
}