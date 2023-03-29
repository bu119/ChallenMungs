package com.ssafy.challenmungs.data.remote.interceptor

import com.ssafy.challenmungs.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class WalletAuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", BuildConfig.KLAYTN_AUTHORIZATION)
            .addHeader("x-chain-id", BuildConfig.KLAYTN_X_CHAIN_ID)
            .build()

        return chain.proceed(request)
    }
}