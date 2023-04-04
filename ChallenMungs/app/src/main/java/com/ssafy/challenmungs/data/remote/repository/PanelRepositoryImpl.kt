package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelRemoteDataSource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.repository.PanelRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PanelRepositoryImpl @Inject constructor(
    private val panelRemoteDataSource: PanelRemoteDataSource
) : PanelRepository {

    override suspend fun requestPanelChallengeInfo(challengeId: Long): Resource<ChallengeInfo> =
        wrapToResource(Dispatchers.IO) {
            panelRemoteDataSource.getPanelChallengeInfo(challengeId).toDomainModel()
        }

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
    ): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            panelRemoteDataSource.createPanelChallenge(
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
            ).toDomainModel()
        }
}