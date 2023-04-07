package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.klaytn.WalletRemoteDataSource
import com.ssafy.challenmungs.domain.entity.klaytn.Account
import com.ssafy.challenmungs.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletRemoteDataSource: WalletRemoteDataSource
) : WalletRepository {

    override suspend fun createAccount(): Resource<Account> = wrapToResource(Dispatchers.IO) {
        walletRemoteDataSource.createAccount().toDomainModel()
    }
}