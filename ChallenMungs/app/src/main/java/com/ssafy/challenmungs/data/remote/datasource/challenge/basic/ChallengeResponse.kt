package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName

data class ChallengeResponse(
    @SerializedName("challengeId")
    val challengeId: Int,
    @SerializedName("challengeType")
    val challengeType: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("period")
    val period: Long?,
    @SerializedName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerializedName("currentParticipantCount")
    val currentParticipantCount: Int,
    @SerializedName("entryFee")
    val entryFee: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("campaignId")
    val campaignId: Int?,
    @SerializedName("successCondition")
    val successCondition: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("gameType")
    val gameType: Int?,
    @SerializedName("centerLat")
    val centerLat: Double?,
    @SerializedName("centerLng")
    val centerLng: Double?,
    @SerializedName("cellD")
    val cellD: Int?,
    @SerializedName("maxLat")
    val maxLat: Double?,
    @SerializedName("minLat")
    val minLat: Double?,
    @SerializedName("maxLng")
    val maxLng: Double?,
    @SerializedName("minLng")
    val minLng: Double?,
    @SerializedName("cellSize")
    val cellSize: Double?,
    @SerializedName("map_size")
    val mapSize: Int?,
)
