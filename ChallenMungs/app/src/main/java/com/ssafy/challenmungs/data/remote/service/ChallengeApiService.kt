package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeListResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicHistoryResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeBasicTodayResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeParticipantResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChallengeApiService {

    @POST("/challenge/tokenConfirm/getList")
    suspend fun getChallengeList(
        @Query("type") type: Int,
        @Query("searchValue") searchValue: String?,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("myChallenge") myChallenge: Boolean = false,
        @Query("onlyTomorrow") onlyTomorrow: Boolean = false
    ): ChallengeListResponse

    @POST("/challenge/getChallengeInfo")
    suspend fun getChallengeInfo(
        @Query("challengeId") challengeId: Int
    ): ChallengeInfoResponse

    @GET("/generalBoard/tokenConfirm/getToday")
    suspend fun getBasicToday(
        @Query("challengeId") challengeId: Int
    ): List<ChallengeBasicTodayResponse>

    @GET("/generalBoard/tokenConfirm/history")
    suspend fun getBasicHistory(
        @Query("challengeId") challengeId: Int,
        @Query("boardUserId") targetMemberId: String
    ): List<ChallengeBasicHistoryResponse>

    @GET("/challenge/tokenConfirm/participants")
    suspend fun getParticipants(
        @Query("challengeId") challengeId: Int
    ): List<ChallengeParticipantResponse>

    @POST("/challenge/tokenConfirm/isHere")
    suspend fun getChallengeParticipationFlag(
        @Query("challengeId") challengeId: Long
    ): ResultResponse

    @POST("/challenge/tokenConfirm/getInChallenge")
    suspend fun requestParticipate(
        @Query("challengeId") challengeId: Long,
        @Query("teamId") teamId: Int?
    ): ResultResponse

    @POST("/challenge/tokenConfirm/getOutChallenge")
    suspend fun requestWithDraw(
        @Query("challengeId") challengeId: Long,
    ): ResultResponse

    @POST("/generalBoard/tokenConfirm/reject")
    suspend fun requestReject(
        @Query("boardId") boardId: Int
    ): ResultResponse
}