package com.ssafy.challenmungs.domain.entity.challenge

data class Challenge(
    val challengeId: Int,
    val challengeType: String?,
    val title: String,
    val startDate: String,
    val endDate: String,
    val maxParticipantCount: Int,
    val currentParticipantCount: Int,
    val entryFee: Int,
    val status: Int,
    val campaignId: Int?,
    val successCondition: Int?,
    val description: String?,
    val gameType: Int?,
    val topLeftLat: Double?,
    val topLeftLng: Double?,
    val bottomRightLat: Double?,
    val bottomRightLng: Double?,
    val centerLat: Double?,
    val centerLng: Double?,
    val cellD: Int?,
    val result: String = ""
)
