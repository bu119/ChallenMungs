package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse

interface MyWalletRemoteDataSource {

    suspend fun getTotalDonate(): ResultResponse

    suspend fun getMyWalletBalance(type: String): ResultResponse

    suspend fun getMyWalletHistory(): List<BalanceHistoryResponse>

    suspend fun getPiggyBankHistory(): List<BalanceHistoryResponse>

    suspend fun getDonationHistory(
        loginId: String,
        year: Int
    ): List<BalanceHistoryResponse>

    suspend fun getDonationSummary(loginId: String, year: Int): DonationSummaryResponse

    suspend fun getDonationDetail(donationId: Int): DonationDetailResponse
}