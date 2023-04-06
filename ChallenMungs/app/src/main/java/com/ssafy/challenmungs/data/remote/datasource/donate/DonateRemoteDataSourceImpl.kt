package com.ssafy.challenmungs.data.remote.datasource.donate

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.service.DonateApiService
import javax.inject.Inject

class DonateRemoteDataSourceImpl @Inject constructor(
    private val donateApiService: DonateApiService
) : DonateRemoteDataSource {

    override suspend fun getCampaignList(type: String, sort: Int): List<CampaignListItemResponse> =
        donateApiService.getCampaignList(type, sort)

    override suspend fun getCampaignInfo(campaignId: Int): CampaignInfoResponse =
        donateApiService.getCampaignInfo(campaignId)

    override suspend fun getBalance(type: String): ResultResponse =
        donateApiService.getBalance(type)

    override suspend fun requestDonate(campaignId: Int, money: Int, memo: String): ResultResponse =
        donateApiService.requestDonate(campaignId, money, memo)
}