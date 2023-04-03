package com.ssafy.challenmungs.domain.entity.challenge

import com.ssafy.challenmungs.data.remote.datasource.challenge.panel.Location

data class ChallengeInfo(
    val title: String,
    val centerLat: Double,
    val centerLng: Double,
    val category: String,
    val mapCoordinate: ArrayList<ArrayList<ArrayList<Location>>>,
    val startDate: String,
    val endDate: String,
    val fee: String,
    val totalFee: String,
    val type: String,
    val currentRank: ArrayList<RankDetail>,
    val mapInfo: ArrayList<ArrayList<Int>>
)

data class RankDetail(
    val profileImageUrl: String?,
    val name: String,
    val userId: String,
    val count: Int,
    val rank: Int,
    val teamId: Long,
    var crown: Int?
)