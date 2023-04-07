package com.ssafy.challenmungs.common.util

import android.content.Context
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import org.json.JSONObject

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

@Suppress("DEPRECATION")
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

fun backDoublePressedFragmentCallback(fragment: Fragment): OnBackPressedCallback {
    var backPressedTime: Long = 0
    var toast: Toast = Toast.makeText(fragment.requireActivity(), "", Toast.LENGTH_SHORT)
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() > backPressedTime + 2000) {
                toast = Toast.makeText(
                    fragment.requireActivity(),
                    "한번 더 누르면 종료됩니다.",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                backPressedTime = System.currentTimeMillis()
            } else {
                fragment.requireActivity().finish()
                toast.cancel()
            }
        }
    }

    fragment.requireActivity().onBackPressedDispatcher.addCallback(fragment, callback)

    return callback
}

fun extractIdFromJWT(jwtString: String): String? {
    val parts = jwtString.split('.')
    if (parts.size != 3) {
        // JWT 파트가 3개가 아닐 경우 예외 처리
        return null
    }
    val payload = parts[1]
    // Base64 디코딩
    val decodedPayload = String(Base64.decode(payload, Base64.DEFAULT), Charsets.UTF_8)
    // JSON 파싱
    val json = JSONObject(decodedPayload)
    // 'id' 필드 추출
    return json.optString("loginId")
}