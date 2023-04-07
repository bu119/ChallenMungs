package com.ssafy.challenmungs.common.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.challenmungs.R

object MapHelper {
    private const val UPDATE_INTERVAL = 1000L // 1초
    private const val MIN_UPDATE_INTERVAL = 500L // 0.5초
    private const val MAX_UPDATE_AGE = 50000L
    private const val MIN_UPDATE_DISTANCE = 10f // 10m
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_SETTING_ZOOM = 14f
    const val DISTANCE = 0.007
    val defaultPosition = LatLng(36.107102, 128.416558)

    val locationRequest: LocationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL).apply {
            setMinUpdateDistanceMeters(MIN_UPDATE_DISTANCE)
            setMinUpdateIntervalMillis(MIN_UPDATE_INTERVAL)
        }.build()
    }

    val currentLocationRequest: CurrentLocationRequest by lazy {
        CurrentLocationRequest.Builder()
            .setDurationMillis(UPDATE_INTERVAL)
            .setMaxUpdateAgeMillis(MAX_UPDATE_AGE)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
    }

    fun createRectangle(
        center: LatLng,
        distance: Double
    ): List<LatLng> {
        return listOf(
            LatLng(center.latitude - distance, center.longitude - distance),
            LatLng(center.latitude - distance, center.longitude + distance),
            LatLng(center.latitude + distance, center.longitude + distance),
            LatLng(center.latitude + distance, center.longitude - distance),
        )
    }

    fun checkLocationServicesStatus(activity: Activity?): Boolean {
        val locationManager =
            activity?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(activity: Activity?): LatLng? {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        return if (location != null) {
            LatLng(location.latitude, location.longitude)
        } else
            null
    }

    fun replaceDrawableMarker(
        googleMap: GoogleMap,
        drawable: Drawable?,
        position: LatLng,
        context: Context
    ): Marker? {
        val bitmapDrawable = drawable as VectorDrawable
        val bitmap = bitmapDrawable.toBitmap()
        val pixels = 40.px(context)
        val newBitmap = Bitmap.createScaledBitmap(bitmap, pixels, pixels, true)

        return googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(newBitmap)
                )
                .anchor(0.5f, 0.5f)
        )
    }

    fun getColor(context: Context, teamId: Int) =
        ContextCompat.getColor(
            context, when (teamId) {
                0 -> R.color.trans20_pink_swan
                1 -> R.color.trans50_venetian_red
                2 -> R.color.trans45_blue
                3 -> R.color.trans50_golden_poppy
                4 -> R.color.trans50_pumpkin
                5 -> R.color.trans60_lime_green
                else -> R.color.trans60_blue_violet
            }
        )
}