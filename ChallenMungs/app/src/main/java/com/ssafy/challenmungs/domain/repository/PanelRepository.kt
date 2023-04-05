package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo

interface PanelRepository {

    suspend fun requestPanelChallengeInfo(challengeId: Long): Resource<ChallengeInfo>

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
    ): Resource<String>
}