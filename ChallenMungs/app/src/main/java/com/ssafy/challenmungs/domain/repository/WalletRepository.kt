package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.klaytn.Account

interface WalletRepository {

    suspend fun createAccount(): Resource<Account>
}