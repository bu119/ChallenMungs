package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.*

interface ChallengeRepository {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): Resource<List<Challenge>>

    suspend fun getChallengeInfo(challengeId: Int): Resource<NotStartedChallengeDetail>

    suspend fun getBasicToday(challengeId: Int): Resource<List<ChallengeBasicToday>>

    suspend fun getBasicHistory(
        challengeId: Int,
        targetMemberId: String
    ): Resource<List<ChallengeBasicHistory>>

    suspend fun getParticipants(challengeId: Int): Resource<List<ChallengeMember>>

    suspend fun getChallengeParticipationFlag(challengeId: Long): Resource<Boolean>

    suspend fun requestParticipate(challengeId: Long, teamId: Int?): Resource<String>

    suspend fun requestWithDraw(challengeId: Long): Resource<String>

    suspend fun requestReject(boardId: Int): Resource<String>
}