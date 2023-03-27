package com.ssafy.challenmungs.data.remote.datasource.auth

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import okhttp3.RequestBody

interface AuthRemoteDataSource {

    suspend fun requestLogin(body: RequestBody): LogInResponse

    suspend fun requestJoin(name: String, accessToken: String): JoinResponse

    suspend fun setWallet(memberId: String, piggyBank: String, wallet: String): ResultResponse
}