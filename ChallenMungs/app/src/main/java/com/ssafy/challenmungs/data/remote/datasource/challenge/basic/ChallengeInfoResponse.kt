package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.NotStartedChallenge

data class ChallengeInfoResponse(
    @SerializedName("challenge")
    val challenge: ChallengeResponse,
    @SerializedName("participant")
    val participant: List<ParticipantResponse>
) : DataToDomainMapper<NotStartedChallenge> {

    override fun toDomainModel(): NotStartedChallenge = NotStartedChallenge(
        challengeId = challenge.challengeId,
        challengeType = challenge.challengeType,
        title = challenge.title,
        startDate = challenge.startDate.slice(IntRange(5, challenge.startDate.length - 1)),
        endDate = challenge.endDate.slice(IntRange(5, challenge.endDate.length - 1)),
        maxParticipantCount = challenge.maxParticipantCount,
        currentParticipantCount = challenge.currentParticipantCount,
        entryFee = challenge.entryFee,
        status = challenge.status,
        campaignId = challenge.campaignId,
        successCondition = challenge.successCondition,
        description = challenge.description,
        gameType = challenge.gameType,
        centerLat = challenge.centerLat,
        centerLng = challenge.centerLng,
        cellD = challenge.cellD,
        maxLat = challenge.maxLat,
        minLat = challenge.minLat,
        maxLng = challenge.maxLng,
        minLng = challenge.minLng,
        cellSize = challenge.cellSize,
        mapSize = challenge.mapSize,
        participants = participant.map { it.toDomainModel() }
    )
}
