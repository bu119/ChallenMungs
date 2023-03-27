package com.ssafy.challenmungs.domain.usecase.klaytn

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.klaytn.Account
import com.ssafy.challenmungs.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateAccountUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(): Resource<Account> =
        withContext(Dispatchers.IO) {
            walletRepository.createAccount()
        }
}