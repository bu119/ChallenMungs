package com.ssafy.challenmungs.data.remote.datasource.auth

import okhttp3.RequestBody

interface AuthRemoteDataSource {

    suspend fun requestLogin(body: RequestBody): LogInResponse

    suspend fun requestJoin(name: String, accessToken: String): JoinResponse
}