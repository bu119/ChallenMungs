package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.datasource.donate.CampaignInfoResponse
import com.ssafy.challenmungs.data.remote.datasource.donate.CampaignListItemResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DonateApiService {

    @GET("/campaign/list/ongoing")
    suspend fun getCampaignList(
        @Query("type") type: String,
        @Query("sort") sort: Int
    ): List<CampaignListItemResponse>

    @GET("/campaign/content/detail")
    suspend fun getCampaignInfo(
        @Query("campaignId") campaignId: Int
    ): CampaignInfoResponse

    @GET("/wallet/tokenConfirm/balance")
    suspend fun getBalance(
        @Query("type") type: String
    ): ResultResponse

    @POST("/donate/tokenConfirm/sponsor")
    suspend fun requestDonate(
        @Query("campaignId") campaignId: Int,
        @Query("money") money: Int,
        @Query("memo") memo: String,
    ): ResultResponse
}