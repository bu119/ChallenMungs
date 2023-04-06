package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicToday

data class ChallengeBasicTodayResponse(
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
    @SerializedName("user")
    val user: String,
) : DataToDomainMapper<ChallengeBasicToday> {

    override fun toDomainModel(): ChallengeBasicToday =
        ChallengeBasicToday(boardId, myRejectState, user, name, profileUrl, pictureUrl)
}