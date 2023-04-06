package com.ssafy.challenmungs.data.remote.service

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.datasource.mypage.BalanceHistoryResponse
import com.ssafy.challenmungs.data.remote.datasource.mypage.DonationDetailResponse
import com.ssafy.challenmungs.data.remote.datasource.mypage.DonationSummaryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWalletApiService {

    @GET("/wallet/tokenConfirm/totalDonate")
    suspend fun getTotalDonate(): ResultResponse

    @GET("/wallet/tokenConfirm/balance")
    suspend fun getMyWalletBalance(@Query("type") type: String): ResultResponse

    @GET("/wallet/tokenConfirm/myWalletHistory")
    suspend fun getMyWalletHistory(): List<BalanceHistoryResponse>

    @GET("/wallet/tokenConfirm/myPiggyBankHistory")
    suspend fun getPiggyBankHistory(): List<BalanceHistoryResponse>

    @GET("/donate/tokenConfirm/donateList")
    suspend fun getDonationHistory(
        @Query("loginId") loginId: String,
        @Query("year") year: Int
    ): List<BalanceHistoryResponse>

    @GET("/donate/tokenConfirm/donateSummary")
    suspend fun getDonationSummary(
        @Query("loginId") loginId: String,
        @Query("year") year: Int
    ): DonationSummaryResponse

    @GET("/donate/tokenConfirm/donateDetail")
    suspend fun getDonationDetail(
        @Query("donationId") donationId: Int
    ): DonationDetailResponse
}