package com.ssafy.challenmungs.domain.usecase.mypage

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.repository.MyWalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDonationHistoryUseCase @Inject constructor(
    private val myWalletRepository: MyWalletRepository
) {
    suspend operator fun invoke(loginId: String, year: Int): Resource<List<BalanceHistory>> =
        withContext(Dispatchers.IO) {
            myWalletRepository.getDonationHistory(loginId, year)
        }
}