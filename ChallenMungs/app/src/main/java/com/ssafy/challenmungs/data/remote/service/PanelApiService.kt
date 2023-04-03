package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelInfoResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface PanelApiService {

    @POST("/panel/tokenConfirm/getInfo")
    suspend fun getPanelChallengeInfo(@Query("challengeId") challengeId: Long): PanelInfoResponse
}