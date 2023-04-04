package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.donate.CampaignInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.donate.CampaignListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DonateApiService {

    @GET("/campaign/list/ongoing")
    suspend fun getCampaignList(
        @Query("type") type: String,
        @Query("sort") sort: Int
    ): List<CampaignListResponse>

    @GET("/campaign/content/detail")
    suspend fun getCampaignInfo(@Query("campaignId") campaignId: Int): CampaignInfoResponse
}