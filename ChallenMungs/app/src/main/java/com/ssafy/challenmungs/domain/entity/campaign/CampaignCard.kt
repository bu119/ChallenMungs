package com.ssafy.challenmungs.domain.entity.campaign

data class CampaignCard(
    val campaignId: Int,
    val bannerUrl: String,
    val title: String,
    val charity: String,
    val cheerCount: String,
    val progress: Int,
    val target: Int,
    val achievement: Int
)
