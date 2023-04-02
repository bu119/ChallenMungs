package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.auth.JoinResponse
import com.ssafy.challenmungs.data.remote.datasource.auth.LogInResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("/user/kakaoLogin")
    suspend fun requestLogin(@Body body: RequestBody): LogInResponse

    @POST("/user/registerUser")
    suspend fun requestJoin(
        @Query("name") name: String,
        @Query("accessKey") accessToken: String
    ): JoinResponse
}