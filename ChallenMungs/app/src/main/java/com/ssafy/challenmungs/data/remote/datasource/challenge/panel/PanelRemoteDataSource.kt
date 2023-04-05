package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface PanelRemoteDataSource {

    suspend fun getPanelChallengeInfo(challengeId: Long): PanelInfoResponse

    suspend fun createPanelChallenge(
        title: String,
        startDate: String,
        endDate: String,
        maxParticipantCount: Int,
        gameType: Int,
        entryFee: Int,
        centerLat: Double,
        centerLng: Double,
        mapSize: Double,
        cellSize: Double,
    ): ResultResponse
}