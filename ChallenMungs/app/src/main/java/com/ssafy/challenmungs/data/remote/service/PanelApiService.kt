package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface PanelApiService {

    @POST("/panel/tokenConfirm/getInfo")
    suspend fun getPanelChallengeInfo(@Query("challengeId") challengeId: Long): PanelInfoResponse

    @POST("/panel/tokenConfirm/makePanelChallenge")
    suspend fun createPanelChallenge(
        @Query("title") title: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("maxParticipantCount") maxParticipantCount: Int,
        @Query("gameType") gameType: Int,
        @Query("entryFee") entryFee: Int,
        @Query("centerLat") centerLat: Double,
        @Query("centerLng") centerLng: Double,
        @Query("mapSize") mapSize: Double,
        @Query("cellSize") cellSize: Double
    ): ResultResponse
}