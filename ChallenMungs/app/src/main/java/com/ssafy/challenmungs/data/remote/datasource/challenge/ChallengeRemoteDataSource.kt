package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface ChallengeRemoteDataSource {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): ChallengeListResponse

    suspend fun getChallengeInfo(challengeId: Int): ChallengeInfoResponse

    suspend fun requestParticipate(challengeId: Long, teamId: Int?): ResultResponse
}