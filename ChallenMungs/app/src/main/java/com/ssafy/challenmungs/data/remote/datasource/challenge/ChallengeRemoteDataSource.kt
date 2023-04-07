package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicHistoryResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicTodayResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeParticipantResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface ChallengeRemoteDataSource {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): ChallengeListResponse

    suspend fun getChallengeInfo(challengeId: Int): ChallengeInfoResponse

    suspend fun getBasicToday(challengeId: Int): List<ChallengeBasicTodayResponse>

    suspend fun getBasicHistory(
        challengeId: Int,
        targetMemberId: String
    ): List<ChallengeBasicHistoryResponse>

    suspend fun getParticipants(challengeId: Int): List<ChallengeParticipantResponse>

    suspend fun getChallengeParticipationFlag(challengeId: Long): ResultResponse

    suspend fun requestParticipate(challengeId: Long, teamId: Int?): ResultResponse

    suspend fun requestWithDraw(challengeId: Long): ResultResponse

    suspend fun requestReject(boardId: Int): ResultResponse
}