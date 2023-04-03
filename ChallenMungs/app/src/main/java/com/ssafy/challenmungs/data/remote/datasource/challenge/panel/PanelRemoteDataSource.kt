package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

interface PanelRemoteDataSource {

    suspend fun getChallengeInfo(challengeId: Long): PanelInfoResponse
}