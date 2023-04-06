package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.ssafy.challenmungs.data.remote.datasource.common.ResultResponse
import com.ssafy.challenmungs.data.remote.service.MyWalletApiService
import javax.inject.Inject

class MyWalletRemoteDataSourceImpl @Inject constructor(
    private val myWalletApiService: MyWalletApiService
) : MyWalletRemoteDataSource {

    override suspend fun getTotalDonate(): ResultResponse =
        myWalletApiService.getTotalDonate()

    override suspend fun getMyWalletBalance(type: String): ResultResponse =
        myWalletApiService.getMyWalletBalance(type)

    override suspend fun getMyWalletHistory(): List<BalanceHistoryResponse> =
        myWalletApiService.getMyWalletHistory()

    override suspend fun getPiggyBankHistory(): List<BalanceHistoryResponse> =
        myWalletApiService.getPiggyBankHistory()

    override suspend fun getDonationHistory(
        loginId: String,
        year: Int
    ): List<BalanceHistoryResponse> =
        myWalletApiService.getDonationHistory(loginId, year)

    override suspend fun getDonationSummary(
        loginId: String,
        year: Int
    ): DonationSummaryResponse =
        myWalletApiService.getDonationSummary(loginId, year)

    override suspend fun getDonationDetail(donationId: Int): DonationDetailResponse =
        myWalletApiService.getDonationDetail(donationId)
}