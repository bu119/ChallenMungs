package com.ssafy.challenmungs.common.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat

object PermissionHelper {

    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val CAMERA_PERMISSION = Manifest.permission.CAMERA

    fun hasFineLocationPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun hasCoarseLocationPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun hasCameraPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        CAMERA_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    fun hasLocationPermission(context: Context): Boolean = ContextCompat.checkSelfPermission(
        context,
        ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

    fun launchPermissionSettings(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }
}