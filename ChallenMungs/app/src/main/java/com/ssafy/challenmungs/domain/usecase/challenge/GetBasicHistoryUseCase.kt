package com.ssafy.challenmungs.domain.usecase.challenge

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicHistory
import com.ssafy.challenmungs.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBasicHistoryUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        challengeId: Int,
        targetMemberId: String
    ): Resource<List<ChallengeBasicHistory>> = withContext(Dispatchers.IO) {
        challengeRepository.getBasicHistory(challengeId, targetMemberId)
    }
}