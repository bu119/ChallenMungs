package com.ssafy.challenmungs

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.ssafy.challenmungs.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)

        KakaoSdk.init(this@ApplicationClass, getString(R.string.KAKAO_NATIVE_APP_KEY))
        Log.d("KaKao-KeyHash", Utility.getKeyHash(this@ApplicationClass))
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}