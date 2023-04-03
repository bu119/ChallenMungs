package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo

interface PanelRepository {

    suspend fun requestChallengeInfo(challengeId:Long):Resource<ChallengeInfo>
}