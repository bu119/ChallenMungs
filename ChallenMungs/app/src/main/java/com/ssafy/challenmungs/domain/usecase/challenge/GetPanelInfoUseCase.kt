package com.ssafy.challenmungs.domain.usecase.challenge

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.repository.PanelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPanelInfoUseCase @Inject constructor(
    private val panelRepository: PanelRepository
) {
    suspend operator fun invoke(challengeId: Long): Resource<ChallengeInfo> =
        withContext(Dispatchers.IO) {
            panelRepository.requestPanelChallengeInfo(challengeId)
        }
}