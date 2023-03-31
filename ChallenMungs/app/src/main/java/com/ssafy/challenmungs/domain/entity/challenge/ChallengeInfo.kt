package com.ssafy.challenmungs.domain.entity.challenge

data class ChallengeInfo(
    val title: String,
    val category: String,
    val startDate: String,
    val endDate: String,
    val fee: String,
    val type: String,
    val centerLat: String,
    val centerLng: String,
    val maxParticipantCount: Int,
    val currentParticipantCount: Int,
    val participant: Participant,
)