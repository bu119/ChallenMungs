package com.ssafy.challenmungs.data.remote.datasource.challenge

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
}