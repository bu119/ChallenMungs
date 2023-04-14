package com.ssafy.challenmungs.domain.usecase.mypage

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.MyWalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyWalletBalanceUseCase @Inject constructor(
    private val myWalletRepository: MyWalletRepository
) {
    suspend operator fun invoke(type: String): Resource<String> = withContext(Dispatchers.IO) {
        myWalletRepository.getMyWalletBalance(type)
    }
}