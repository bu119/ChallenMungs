package com.ssafy.challenmungs.domain.entity.challenge

data class ChallengeBasicHistory(
    val boardId: Int,
    val myRejectState: Boolean,
    val name: String,
    val pictureUrl: String,
    val profileUrl: String,
    val registerDay: String,
    val rejectResult: Boolean,
    val user: String,
)
