package com.ssafy.challenmungs.data.remote.datasource.donate

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.campaign.CampaignContent

data class CampaignContentResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("body")
    val body: String,
) : DataToDomainMapper<CampaignContent> {

    override fun toDomainModel(): CampaignContent = CampaignContent(type, body)
}
