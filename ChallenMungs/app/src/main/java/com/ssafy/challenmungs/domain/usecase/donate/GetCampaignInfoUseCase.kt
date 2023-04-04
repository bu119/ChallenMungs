package com.ssafy.challenmungs.domain.usecase.donate

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.Campaign
import com.ssafy.challenmungs.domain.repository.DonateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampaignInfoUseCase @Inject constructor(
    private val donateRepository: DonateRepository
) {
    suspend operator fun invoke(campaignId: Int): Resource<Campaign> = withContext(Dispatchers.IO) {
        donateRepository.getCampaignInfo(campaignId)
    }
}