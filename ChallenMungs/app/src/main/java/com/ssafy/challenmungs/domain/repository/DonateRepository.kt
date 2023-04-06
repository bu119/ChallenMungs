package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard

interface DonateRepository {

    suspend fun getCampaignList(type: String, sort: Int): Resource<List<CampaignCard>>

    suspend fun getBalance(type: String): Resource<String>

    suspend fun requestDonate(campaignId: Int, money: Int, memo: String): Resource<String>
}