package com.ssafy.challenmungs.domain.usecase.challenge

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.PanelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePanelChallengeUseCase @Inject constructor(
    private val panelRepository: PanelRepository
) {
    suspend operator fun invoke(
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
    ): Resource<String> =
        withContext(Dispatchers.IO) {
            panelRepository.createPanelChallenge(
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
}