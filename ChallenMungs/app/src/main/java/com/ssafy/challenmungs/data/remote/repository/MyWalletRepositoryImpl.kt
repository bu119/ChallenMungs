package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.mypage.MyWalletRemoteDataSource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.entity.mypage.DonationDetail
import com.ssafy.challenmungs.domain.entity.mypage.DonationSummary
import com.ssafy.challenmungs.domain.repository.MyWalletRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MyWalletRepositoryImpl @Inject constructor(
    private val myWalletRemoteDataSource: MyWalletRemoteDataSource
) : MyWalletRepository {

    override suspend fun getTotalDonate(): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getTotalDonate().toDomainModel()
        }

    override suspend fun getMyWalletBalance(type: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getMyWalletBalance(type).toDomainModel()
        }

    override suspend fun getMyWalletHistory(): Resource<List<BalanceHistory>> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getMyWalletHistory().map { it.toDomainModel() }
        }

    override suspend fun getPiggyBankHistory(): Resource<List<BalanceHistory>> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getPiggyBankHistory().map { it.toDomainModel() }
        }

    override suspend fun getDonationHistory(
        loginId: String,
        year: Int
    ): Resource<List<BalanceHistory>> = wrapToResource(Dispatchers.IO) {
        myWalletRemoteDataSource.getDonationHistory(loginId, year).map { it.toDomainModel() }
    }

    override suspend fun getDonationSummary(loginId: String, year: Int): Resource<DonationSummary> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getDonationSummary(loginId, year).toDomainModel()
        }

    override suspend fun getDonationDetail(donationId: Int): Resource<DonationDetail> =
        wrapToResource(Dispatchers.IO) {
            myWalletRemoteDataSource.getDonationDetail(donationId).toDomainModel()
        }
}