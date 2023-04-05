package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.Challenge

interface ChallengeRepository {

    suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): Resource<List<Challenge>>
}