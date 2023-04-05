package com.ssafy.challenmungs.domain.entity.challenge

data class NotStartedChallenge(
    val challengeId: Int,
    val challengeType: Int,
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
    val centerLat: Double?,
    val centerLng: Double?,
    val cellD: Int?,
    val maxLat: Double?,
    val minLat: Double?,
    val maxLng: Double?,
    val minLng: Double?,
    val cellSize: Double?,
    val mapSize: Int?,
    val participants: List<Participant>
)
