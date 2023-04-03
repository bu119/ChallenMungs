package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.PanelRemoteDataSource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.repository.PanelRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PanelRepositoryImpl @Inject constructor(
    private val panelRemoteDataSource: PanelRemoteDataSource
) : PanelRepository {

    override suspend fun requestChallengeInfo(challengeId: Long): Resource<ChallengeInfo> =
        wrapToResource(Dispatchers.IO) {
            panelRemoteDataSource.getChallengeInfo(challengeId).toDomainModel()
        }
}