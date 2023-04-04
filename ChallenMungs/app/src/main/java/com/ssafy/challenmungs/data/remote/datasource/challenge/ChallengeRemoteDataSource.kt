package com.ssafy.challenmungs.data.remote.datasource.challenge

interface ChallengeRemoteDataSource {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): ChallengeListResponse
}