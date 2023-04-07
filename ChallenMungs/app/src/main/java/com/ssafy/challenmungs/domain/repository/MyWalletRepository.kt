package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.entity.mypage.DonationDetail
import com.ssafy.challenmungs.domain.entity.mypage.DonationSummary

interface MyWalletRepository {

    suspend fun getTotalDonate(): Resource<String>

    suspend fun getMyWalletBalance(type: String): Resource<String>

    suspend fun getMyWalletHistory(): Resource<List<BalanceHistory>>

    suspend fun getPiggyBankHistory(): Resource<List<BalanceHistory>>

    suspend fun getDonationHistory(loginId: String, year: Int): Resource<List<BalanceHistory>>

    suspend fun getDonationSummary(loginId: String, year: Int): Resource<DonationSummary>

    suspend fun getDonationDetail(donationId: Int): Resource<DonationDetail>
}