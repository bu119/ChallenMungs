package com.ssafy.challenmungs.common.util

import com.google.android.gms.maps.model.LatLng

object Map {

    val defaultPosition = LatLng(36.107102, 128.416558)

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
}