package com.ssafy.challenmungs.domain.entity.campaign

data class Campaign(
    val title: String,
    val thumbnail: String,
    val shelterName: String,
    val loveCount: Int,
    val collectAmount: Int,
    val targetAmount: Int,
    val achievePercent: Int,
    val contentList: List<CampaignContent>,
)
