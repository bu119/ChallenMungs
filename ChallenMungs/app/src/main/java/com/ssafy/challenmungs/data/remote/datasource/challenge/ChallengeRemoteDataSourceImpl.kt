package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicHistoryResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicTodayResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeParticipantResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.service.ChallengeApiService
import javax.inject.Inject

class ChallengeRemoteDataSourceImpl @Inject constructor(
    private val challengeApiService: ChallengeApiService
) : ChallengeRemoteDataSource {

    override suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): ChallengeListResponse = challengeApiService.getChallengeList(type, searchValue, lat, lng)

    override suspend fun getChallengeInfo(challengeId: Int): ChallengeInfoResponse =
        challengeApiService.getChallengeInfo(challengeId)

    override suspend fun getBasicToday(challengeId: Int): List<ChallengeBasicTodayResponse> =
        challengeApiService.getBasicToday(challengeId)

    override suspend fun getBasicHistory(
        challengeId: Int,
        targetMemberId: String
    ): List<ChallengeBasicHistoryResponse> =
        challengeApiService.getBasicHistory(challengeId, targetMemberId)

    override suspend fun getParticipants(challengeId: Int): List<ChallengeParticipantResponse> =
        challengeApiService.getParticipants(challengeId)

    override suspend fun getChallengeParticipationFlag(challengeId: Long): ResultResponse =
        challengeApiService.getChallengeParticipationFlag(challengeId)

    override suspend fun requestParticipate(challengeId: Long, teamId: Int?): ResultResponse =
        challengeApiService.requestParticipate(challengeId, teamId)

    override suspend fun requestWithDraw(challengeId: Long): ResultResponse =
        challengeApiService.requestWithDraw(challengeId)

    override suspend fun requestReject(boardId: Int): ResultResponse =
        challengeApiService.requestReject(boardId)
}