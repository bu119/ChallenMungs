package com.example.challenmungs

import android.app.Application
import com.example.challenmungs.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}