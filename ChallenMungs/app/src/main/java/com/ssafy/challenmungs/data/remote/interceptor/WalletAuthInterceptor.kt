package com.ssafy.challenmungs.data.remote.interceptor

import com.ssafy.challenmungs.data.local.datasource.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class WalletAuthInterceptor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", preferences.authorization)
            .addHeader("x-chain-id", preferences.xChainId).build()

        return chain.proceed(request)
    }
}