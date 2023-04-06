package com.ssafy.challenmungs.domain.usecase.mypage

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.MyWalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTotalDonateUseCase @Inject constructor(
    private val myWalletRepository: MyWalletRepository
) {
    suspend operator fun invoke(): Resource<String> = withContext(Dispatchers.IO) {
        myWalletRepository.getTotalDonate()
    }
}