package com.ssafy.challenmungs.domain.entity.challenge

data class ChallengeInfo(
    val title: String,
    val category: String,
    val startDate: String,
    val endDate: String,
    val fee: String,
    val totalFee: String,
    val type: String,
    val currentRank: ArrayList<RankDetail>,
)

data class RankDetail(
    val profileImageUrl: String?,
    val name: String,
    val count: Int,
    val rank: Int,
    val teamId: Int,
    val crown: Int?
)