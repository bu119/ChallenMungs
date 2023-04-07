package com.ssafy.challenmungs.data.remote.datasource.challenge.basic

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.Participant

data class ParticipantResponse(
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("name")
    val name: String,
) : DataToDomainMapper<Participant> {

    override fun toDomainModel(): Participant = Participant(profile, name)
}
