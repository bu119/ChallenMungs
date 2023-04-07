package com.ssafy.challenmungs.data.remote.datasource.donate

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard

data class CampaignListItemResponse(
    @SerializedName("campaignId")
    val campaignId: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("collectAmount")
    val collectAmount: Int,
    @SerializedName("targetAmount")
    val targetAmount: Int,
    @SerializedName("loveCount")
    val loveCount: Int,
) : DataToDomainMapper<CampaignCard> {

    override fun toDomainModel(): CampaignCard = CampaignCard(
        campaignId = campaignId,
        bannerUrl = thumbnail,
        title = title,
        charity = name,
        cheerCount = loveCount,
        progress = collectAmount * 100 / targetAmount,
        target = targetAmount,
        achievement = collectAmount
    )
}