package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.domain.entity.challenge.NotStartedChallengeDetail

interface ChallengeRepository {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): Resource<List<Challenge>>

    suspend fun getChallengeInfo(challengeId: Int): Resource<NotStartedChallengeDetail>

    suspend fun getChallengeParticipationFlag(challengeId: Long): Resource<Boolean>

    suspend fun requestParticipate(challengeId: Long, teamId: Int?): Resource<String>
}