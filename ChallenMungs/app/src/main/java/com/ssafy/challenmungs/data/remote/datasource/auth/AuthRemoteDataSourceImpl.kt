package com.ssafy.challenmungs.data.remote.datasource.auth

import com.ssafy.challenmungs.data.remote.service.AuthApiService
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRemoteDataSource {

    override suspend fun requestLogin(body: RequestBody): LogInResponse =
        authApiService.requestLogin(body)
}