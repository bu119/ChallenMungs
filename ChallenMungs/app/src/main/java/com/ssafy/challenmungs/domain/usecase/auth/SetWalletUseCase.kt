package com.ssafy.challenmungs.domain.usecase.auth

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetWalletUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        memberId: String,
        piggyBank: String,
        wallet: String
    ): Resource<String> =
        withContext(Dispatchers.IO) {
            authRepository.setWallet(memberId, piggyBank, wallet)
        }
}