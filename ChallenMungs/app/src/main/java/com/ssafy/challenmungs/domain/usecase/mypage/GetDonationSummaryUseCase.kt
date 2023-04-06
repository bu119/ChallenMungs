package com.ssafy.challenmungs.domain.usecase.mypage

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.DonationSummary
import com.ssafy.challenmungs.domain.repository.MyWalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDonationSummaryUseCase @Inject constructor(
    private val myWalletRepository: MyWalletRepository
) {
    suspend operator fun invoke(loginId: String, year: Int): Resource<DonationSummary> = withContext(Dispatchers.IO) {
        myWalletRepository.getDonationSummary(loginId, year)
    }
}