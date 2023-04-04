package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.Challenge

data class ChallengeItemNotFinishedResponse(
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
    @SerializedName("maxLat")
    val topLeftLat: Double?,
    @SerializedName("minLng")
    val topLeftLng: Double?,
    @SerializedName("minLat")
    val bottomRightLat: Double?,
    @SerializedName("maxLng")
    val bottomRightLng: Double?,
    @SerializedName("centerLat")
    val centerLat: Double?,
    @SerializedName("centerLng")
    val centerLng: Double?,
    @SerializedName("cellD")
    val cellD: Int?,
) : DataToDomainMapper<Challenge> {

    override fun toDomainModel(): Challenge {
        val challengeType: String? = when (this.challengeType) {
            1 -> "일반"
            2 -> {
                if (gameType == 1) "판넬(개)"
                else "판넬(팀)"
            }
            3 -> "보물"
            else -> null
        }

        return Challenge(
            challengeId,
            challengeType,
            title,
            startDate.slice(IntRange(5, startDate.length - 1)),
            endDate.slice(IntRange(5, endDate.length - 1)),
            maxParticipantCount,
            currentParticipantCount,
            entryFee,
            status,
            campaignId,
            successCondition,
            description,
            gameType,
            topLeftLat,
            topLeftLng,
            bottomRightLat,
            bottomRightLng,
            centerLat,
            centerLng,
            cellD
        )
    }
}
