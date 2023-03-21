package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.auth.AuthResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/user/kakaoLogin")
    suspend fun requestLogin(@Body body: RequestBody): AuthResponse
}