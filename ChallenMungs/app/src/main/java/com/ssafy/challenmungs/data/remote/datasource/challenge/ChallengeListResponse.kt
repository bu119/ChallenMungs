package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeListResponse(
    @SerializedName("0")
    val ListNotStarted: List<ChallengeItemNotFinishedResponse>,
    @SerializedName("1")
    val ListStarted: List<ChallengeItemNotFinishedResponse>,
    @SerializedName("2")
    val ListFinished: List<ChallengeItemFinishedResponse>
)
