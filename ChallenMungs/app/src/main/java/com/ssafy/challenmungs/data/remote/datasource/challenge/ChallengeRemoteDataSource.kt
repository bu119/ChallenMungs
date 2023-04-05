package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.ssafy.challenmungs.data.remote.datasource.challenge.basic.ChallengeInfoResponse

interface ChallengeRemoteDataSource {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): ChallengeListResponse

    suspend fun getChallengeInfo(challengeId: Int): ChallengeInfoResponse
}