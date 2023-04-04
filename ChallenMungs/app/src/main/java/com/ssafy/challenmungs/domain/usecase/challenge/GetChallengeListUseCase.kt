package com.ssafy.challenmungs.domain.usecase.challenge

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetChallengeListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        type: Int,
        searchValue: String?,
        lat: Double = 36.107102,
        lng: Double = 128.416558
    ): Resource<List<Challenge>> = withContext(Dispatchers.IO) {
        challengeRepository.getChallengeList(type, searchValue, lat, lng)
    }
}