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
    var currentRank: ArrayList<RankDetail>,
    val mapInfo: ArrayList<ArrayList<Int>>,
    val teamDraw: Boolean,
)

data class RankDetail(
    val profile: String?,
    val name: String,
    val loginId: String,
    val point: Int,
    val teamRank: Int,
    val indiRank: Int,
    val teamId: Int,
    var crown: Int?
)