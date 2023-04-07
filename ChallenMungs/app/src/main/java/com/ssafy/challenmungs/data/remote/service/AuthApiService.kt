package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.auth.JoinResponse
import com.ssafy.challenmungs.data.remote.datasource.auth.LogInResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
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

    @POST("/user/charityRegister")
    suspend fun requestShelterJoin(
        @Query("charityName") shelterName: String,
        @Query("inviteCode") inviteCode: String,
        @Query("loginId") memberId: String,
        @Query("password") password: String
    ): ResultResponse

    @POST("/user/codeEmail")
    suspend fun requestInviteCode(
        @Query("charityName") shelterName: String,
        @Query("to") email: String
    ): ResultResponse
}