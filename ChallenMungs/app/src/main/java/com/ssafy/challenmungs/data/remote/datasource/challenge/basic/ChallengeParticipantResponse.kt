package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeMember

data class ChallengeParticipantResponse(
    @SerializedName("challengeId")
    val challengeId: Int,
    @SerializedName("myChallengeId")
    val myChallengeId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("successCount")
    val successCount: Int,
    @SerializedName("user")
    val user: String,
) : DataToDomainMapper<ChallengeMember> {

    override fun toDomainModel(): ChallengeMember = ChallengeMember(
        challengeId = challengeId,
        memberId = user,
        memberName = name,
        profileUrl = profileUrl,
        successCount = successCount
    )
}
