package com.ssafy.challenmungs.domain.usecase.donate

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.repository.DonateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestDonateUseCase @Inject constructor(
    private val donateRepository: DonateRepository
) {
    suspend operator fun invoke(campaignId: Int, money: Int, memo: String): Resource<String> =
        withContext(Dispatchers.IO) {
            donateRepository.requestDonate(campaignId, money, memo)
        }
}