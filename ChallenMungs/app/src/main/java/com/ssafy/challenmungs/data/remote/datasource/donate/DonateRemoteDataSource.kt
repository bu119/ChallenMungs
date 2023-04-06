package com.ssafy.challenmungs.data.remote.datasource.donate

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface DonateRemoteDataSource {

    suspend fun getCampaignList(type: String, sort: Int): List<CampaignResponse>

    suspend fun getBalance(type: String): ResultResponse

    suspend fun requestDonate(campaignId: Int, money: Int, memo: String): ResultResponse
}