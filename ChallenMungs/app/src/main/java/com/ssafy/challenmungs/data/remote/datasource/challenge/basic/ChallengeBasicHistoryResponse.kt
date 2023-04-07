package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicHistory

data class ChallengeBasicHistoryResponse(
    @SerializedName("boardId")
    val boardId: Int,
    @SerializedName("myRejectState")
    val myRejectState: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("pictureUrl")
    val pictureUrl: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("registerDay")
    val registerDay: String,
    @SerializedName("rejectResult")
    val rejectResult: Boolean,
    @SerializedName("user")
    val user: String,
) : DataToDomainMapper<ChallengeBasicHistory> {

    override fun toDomainModel(): ChallengeBasicHistory = ChallengeBasicHistory(
        boardId = boardId,
        memberId = user,
        memberName = name,
        profileUrl = profileUrl,
        pictureUrl = pictureUrl,
        registerDay = registerDay.slice(IntRange(5, registerDay.length - 1)),
        rejectResult = rejectResult,
        myRejectState = myRejectState
    )
}
