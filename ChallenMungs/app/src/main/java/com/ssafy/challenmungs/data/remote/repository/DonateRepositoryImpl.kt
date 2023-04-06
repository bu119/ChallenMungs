package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.donate.DonateRemoteDataSource
import com.ssafy.challenmungs.domain.entity.campaign.Campaign
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard
import com.ssafy.challenmungs.domain.repository.DonateRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DonateRepositoryImpl @Inject constructor(
    private val donateRemoteDataSource: DonateRemoteDataSource
) : DonateRepository {

    override suspend fun getCampaignList(
        type: String,
        sort: Int
    ): Resource<List<CampaignCard>> = wrapToResource(Dispatchers.IO) {
        donateRemoteDataSource.getCampaignList(type, sort).map { it.toDomainModel() }
    }

    override suspend fun getCampaignInfo(campaignId: Int): Resource<Campaign> =
        wrapToResource(Dispatchers.IO) {
            donateRemoteDataSource.getCampaignInfo(campaignId).toDomainModel()
        }

    override suspend fun getBalance(type: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            donateRemoteDataSource.getBalance(type).toDomainModel()
        }

    override suspend fun requestDonate(
        campaignId: Int,
        money: Int,
        memo: String
    ): Resource<String> = wrapToResource(Dispatchers.IO) {
        donateRemoteDataSource.requestDonate(campaignId, money, memo).toDomainModel()
    }
}