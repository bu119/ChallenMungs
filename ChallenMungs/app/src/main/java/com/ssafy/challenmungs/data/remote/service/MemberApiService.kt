package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.datasource.member.MemberResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberApiService {

    @GET("/user/tokenConfirm/getNameAndProfileAndLoginId")
    suspend fun getMemberInfo(): MemberResponse

    @POST("/wallet/tokenConfirm/normal")
    suspend fun setWallet(
        @Query("loginId") memberId: String,
        @Query("piggybank") piggyBank: String,
        @Query("wallet") wallet: String,
    ): ResultResponse
}