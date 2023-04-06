package com.ssafy.challenmungs.domain.usecase.challenge

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeMember
import com.ssafy.challenmungs.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetParticipantsUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: Int): Resource<List<ChallengeMember>> =
        withContext(Dispatchers.IO) {
            challengeRepository.getParticipants(challengeId)
        }
}