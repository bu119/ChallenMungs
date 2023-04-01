package com.ssafy.challenmungs.common.util

import android.app.Activity
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

object MapHelper {
    const val UPDATE_INTERVAL = 1000L // 1초
    const val MIN_UPDATE_INTERVAL = 500L // 0.5초
    const val MIN_UPDATE_DISTANCE = 10f // 10m
    const val DISTANCE = 0.00035
    val defaultPosition = LatLng(36.107102, 128.416558)

    val locationRequest: LocationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL).apply {
            setMinUpdateDistanceMeters(MIN_UPDATE_DISTANCE)
            setMinUpdateIntervalMillis(MIN_UPDATE_INTERVAL)
        }.build()
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

    fun replaceDrawableMarker(
        googleMap: GoogleMap,
        drawable: Drawable?,
        position: LatLng
    ): Marker? {
        val bitmapDrawable = drawable as VectorDrawable
        val bitmap = bitmapDrawable.toBitmap()
        return googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(bitmap)
                )
                .anchor(0.5f, 0.5f)
        )
    }
}