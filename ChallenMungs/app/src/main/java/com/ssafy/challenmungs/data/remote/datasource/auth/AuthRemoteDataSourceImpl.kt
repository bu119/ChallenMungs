package com.ssafy.challenmungs.data.remote.datasource.auth

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.service.AuthApiService
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRemoteDataSource {

    override suspend fun requestLogin(body: RequestBody): LogInResponse =
        authApiService.requestLogin(body)

    override suspend fun requestJoin(name: String, accessToken: String): JoinResponse =
        authApiService.requestJoin(name, accessToken)

    override suspend fun setWallet(
        memberId: String,
        piggyBank: String,
        wallet: String
    ): ResultResponse = authApiService.setWallet(memberId, piggyBank, wallet)
}