package com.ssafy.challenmungs.domain.usecase.donate

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard
import com.ssafy.challenmungs.domain.repository.DonateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampaignListUseCase @Inject constructor(
    private val donateRepository: DonateRepository
) {
    suspend operator fun invoke(type: String, sort: Int): Resource<List<CampaignCard>> =
        withContext(Dispatchers.IO) {
            donateRepository.getCampaignList(type, sort)
        }
}