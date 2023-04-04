package com.ssafy.challenmungs.data.remote.datasource.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeItemFinishedResponse(
    @SerializedName("challengeInfo")
    val challenge: ChallengeItemNotFinishedResponse,
    @SerializedName("successInfo")
    val result: Int
)