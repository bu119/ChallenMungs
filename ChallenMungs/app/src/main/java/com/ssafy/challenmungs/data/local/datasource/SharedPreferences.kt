package com.ssafy.challenmungs.data.local.datasource

import android.content.Context

class SharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var isFirstRun: Boolean
        get() = prefs.getBoolean("isFirstRun", true)
        set(value) = prefs.edit().putBoolean("isFirstRun", value).apply()

    var authorization: String =
        "Basic S0FTSzZGTzE0NjVNRTU0NjkzTDBVWENNOkpJaG5UbXlUZmp2RHdxRFh5Y3NOTmxfWFZsVDMyQTdMNk1ZV0lHR0c="

    var xChainId: String = "1001"

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}