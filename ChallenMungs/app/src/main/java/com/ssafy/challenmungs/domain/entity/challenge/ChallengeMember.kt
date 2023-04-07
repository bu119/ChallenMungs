package com.ssafy.challenmungs.domain.entity.challenge

data class ChallengeMember(
    val challengeId: Int,
    val memberId: String,
    val memberName: String,
    val profileUrl: String,
    val successCount: Int,
    val selected: Boolean = false,
)