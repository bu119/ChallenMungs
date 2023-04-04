package com.ssafy.challenmungs.data.remote.datasource.donate

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.campaign.Campaign

data class CampaignInfoResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("writer")
    val writer: String,
    @SerializedName("lovecnt")
    val loveCount: Int,
    @SerializedName("collectAmount")
    val collectAmount: Int,
    @SerializedName("targetAmount")
    val targetAmount: Int,
    @SerializedName("contentList")
    val contentList: List<CampaignContentResponse>,
) : DataToDomainMapper<Campaign> {

    override fun toDomainModel(): Campaign = Campaign(
        title = title,
        thumbnail = thumbnail,
        writer = writer,
        loveCount = loveCount,
        percent = collectAmount / targetAmount,
        collectAmount = collectAmount,
        targetAmount = targetAmount,
        contentList = contentList.map { it.toDomainModel() }
    )
}