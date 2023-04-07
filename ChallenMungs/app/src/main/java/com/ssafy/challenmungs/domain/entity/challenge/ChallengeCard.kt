package com.ssafy.challenmungs.domain.entity.challenge

data class ChallengeCard(
    val startDate: String,
    val endDate: String,
    val tag: String,
    val title: String,
    val price: Int,
    val headCount: Int,
    val result: String?
)