package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.member.MemberResponse
import retrofit2.http.GET

interface MemberApiService {

    @GET("/user/tokenConfirm/getNameAndProfile")
    suspend fun getMemberInfo(): MemberResponse
}