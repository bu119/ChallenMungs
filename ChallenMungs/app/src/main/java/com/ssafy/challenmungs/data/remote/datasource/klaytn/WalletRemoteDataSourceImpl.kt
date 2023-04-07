package com.ssafy.challenmungs.data.remote.datasource.klaytn

import com.ssafy.challenmungs.data.remote.service.WalletApiService
import javax.inject.Inject

class WalletRemoteDataSourceImpl @Inject constructor(
    private val walletApiService: WalletApiService
) : WalletRemoteDataSource {

    override suspend fun createAccount(): AccountResponse =
        walletApiService.createAccount()
}