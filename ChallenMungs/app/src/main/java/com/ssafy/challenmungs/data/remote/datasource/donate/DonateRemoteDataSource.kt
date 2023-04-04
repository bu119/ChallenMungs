package com.ssafy.challenmungs.data.remote.datasource.donate

interface DonateRemoteDataSource {

    suspend fun getCampaignList(type: String, sort: Int): List<CampaignResponse>
}