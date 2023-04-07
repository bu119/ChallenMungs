package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.klaytn.AccountResponse
import retrofit2.http.POST

interface WalletApiService {

    @POST("/v2/account")
    suspend fun createAccount(): AccountResponse
}