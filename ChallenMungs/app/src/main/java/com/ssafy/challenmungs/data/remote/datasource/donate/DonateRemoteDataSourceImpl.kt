package com.ssafy.challenmungs.data.remote.datasource.donate

import com.ssafy.challenmungs.data.remote.service.DonateApiService
import javax.inject.Inject

class DonateRemoteDataSourceImpl @Inject constructor(
    private val donateApiService: DonateApiService
) : DonateRemoteDataSource {

    override suspend fun getCampaignList(type: String, sort: Int): List<CampaignResponse> =
        donateApiService.getCampaignList(type, sort)
}