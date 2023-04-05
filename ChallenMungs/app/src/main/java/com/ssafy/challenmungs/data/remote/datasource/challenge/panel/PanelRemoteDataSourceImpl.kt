package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.service.PanelApiService
import javax.inject.Inject

class PanelRemoteDataSourceImpl @Inject constructor(
    private val panelApiService: PanelApiService
) : PanelRemoteDataSource {

    override suspend fun getPanelChallengeInfo(challengeId: Long): PanelInfoResponse =
        panelApiService.getPanelChallengeInfo(challengeId)

    override suspend fun createPanelChallenge(
        title: String,
        startDate: String,
        endDate: String,
        maxParticipantCount: Int,
        gameType: Int,
        entryFee: Int,
        centerLat: Double,
        centerLng: Double,
        mapSize: Double,
        cellSize: Double
    ): ResultResponse =
        panelApiService.createPanelChallenge(
            title,
            startDate,
            endDate,
            maxParticipantCount,
            gameType,
            entryFee,
            centerLat,
            centerLng,
            mapSize,
            cellSize
        )
}