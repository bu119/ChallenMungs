package com.ssafy.challenmungs.data.remote.datasource.donate

import com.ssafy.challenmungs.data.remote.service.DonateApiService
import javax.inject.Inject

class DonateRemoteDataSourceImpl @Inject constructor(
    private val donateApiService: DonateApiService
) : DonateRemoteDataSource {

    override suspend fun getCampaignList(type: String, sort: Int): List<CampaignListResponse> =
        donateApiService.getCampaignList(type, sort)

    override suspend fun getCampaignInfo(campaignId: Int): CampaignInfoResponse =
        donateApiService.getCampaignInfo(campaignId)
}