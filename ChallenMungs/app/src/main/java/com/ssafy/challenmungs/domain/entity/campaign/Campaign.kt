package com.ssafy.challenmungs.domain.entity.campaign

data class Campaign(
    val title: String,
    val thumbnail: String,
    val writer: String,
    val loveCount: Int,
    val percent: Int,
    val collectAmount: Int,
    val targetAmount: Int,
    val contentList: List<CampaignContent>,
)