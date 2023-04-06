package com.ssafy.challenmungs.domain.entity.challenge

data class NotStartedChallengeDetail(
    val challengeId: Int,
    val challengeType: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val period: Long?,
    val maxParticipantCount: Int,
    val currentParticipantCount: Int,
    val entryFee: Int,
    val status: Int,
    val campaignId: Int?,
    val successCondition: Int?,
    val description: String?,
    val gameType: String?,
    val centerLat: Double?,
    val centerLng: Double?,
    val cellD: Int?,
    val maxLat: Double?,
    val minLat: Double?,
    val maxLng: Double?,
    val minLng: Double?,
    val cellSize: Double?,
    val mapSize: Int?,
    val participants: ArrayList<Participant>,
    var isParticipated: Boolean = false
)
