package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.donate.CampaignResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DonateApiService {

    @GET("/campaign/list/ongoing")
    suspend fun getCampaignList(
        @Query("type") type: String,
        @Query("sort") sort: Int
    ): List<CampaignResponse>
}