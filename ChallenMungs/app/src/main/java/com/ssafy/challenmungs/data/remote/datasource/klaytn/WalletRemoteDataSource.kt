package com.ssafy.challenmungs.data.remote.datasource.klaytn

interface WalletRemoteDataSource {

    suspend fun createAccount(): AccountResponse
}