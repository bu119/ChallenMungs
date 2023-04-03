package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import com.ssafy.challenmungs.data.remote.service.PanelApiService
import javax.inject.Inject

class PanelRemoteDataSourceImpl @Inject constructor(
    private val panelApiService: PanelApiService
) : PanelRemoteDataSource {

    override suspend fun getChallengeInfo(challengeId: Long): PanelInfoResponse =
        panelApiService.getPanelChallengeInfo(challengeId)
}