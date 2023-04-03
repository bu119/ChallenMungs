package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeListResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface ChallengeApiService {

    @POST("/challenge/tokenConfirm/getList")
    suspend fun getChallengeList(
        @Query("type") type: Int,
        @Query("searchValue") searchValue: String?,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("myChallenge") myChallenge: Boolean = false,
        @Query("onlyTomorrow") onlyTomorrow: Boolean = false
    ): ChallengeListResponse
}