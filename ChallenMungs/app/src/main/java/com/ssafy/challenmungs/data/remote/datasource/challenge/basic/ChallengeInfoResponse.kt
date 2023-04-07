package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.NotStartedChallengeDetail

data class ChallengeInfoResponse(
    @SerializedName("challenge")
    val challenge: ChallengeResponse,
    @SerializedName("participant")
    val participant: List<ParticipantResponse>
) : DataToDomainMapper<NotStartedChallengeDetail> {

    override fun toDomainModel(): NotStartedChallengeDetail = NotStartedChallengeDetail(
        challengeId = challenge.challengeId,
        challengeType = when (challenge.challengeType) {
            1 -> "일반 챌린지"
            2 -> "판넬 뒤집기"
            else -> "보물 찾기"
        },
        title = challenge.title,
        startDate = challenge.startDate.slice(IntRange(5, challenge.startDate.length - 1)),
        endDate = challenge.endDate.slice(IntRange(5, challenge.endDate.length - 1)),
        period = challenge.period,
        maxParticipantCount = challenge.maxParticipantCount,
        currentParticipantCount = challenge.currentParticipantCount,
        entryFee = challenge.entryFee,
        status = challenge.status,
        campaignId = challenge.campaignId,
        successCondition = challenge.successCondition,
        description = challenge.description,
        gameType = if (challenge.gameType != null) {
            if (challenge.gameType == 1) "개인전" else "팀전"
        } else null,
        centerLat = challenge.centerLat,
        centerLng = challenge.centerLng,
        cellD = challenge.cellD,
        maxLat = challenge.maxLat,
        minLat = challenge.minLat,
        maxLng = challenge.maxLng,
        minLng = challenge.minLng,
        cellSize = challenge.cellSize,
        mapSize = challenge.mapSize,
        participants = ArrayList(participant.map { it.toDomainModel() })
    )
}
