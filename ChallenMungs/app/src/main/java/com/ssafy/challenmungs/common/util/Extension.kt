package com.ssafy.challenmungs.common.util

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

private const val HIDE_DELAY_MS = 3000L

fun Int.dp(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

fun Int.px(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun getDeviceWidthPx(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

fun getDeviceHeightPx(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}

@OptIn(DelicateCoroutinesApi::class)
fun AppCompatActivity.setImmersiveMode() {
    // API 30 이상인 경우에는 WindowInsetsController를 사용하여 Fullscreen 모드로 설정
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = window.insetsController ?: return
        controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        GlobalScope.launch(Dispatchers.Main) {
            delay(HIDE_DELAY_MS)
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        }
    } else {
        // API 30 미만인 경우에는 deprecated 된 API를 사용하여 Fullscreen 모드로 설정
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(HIDE_DELAY_MS)
                    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                }
            }
        }
    }
}