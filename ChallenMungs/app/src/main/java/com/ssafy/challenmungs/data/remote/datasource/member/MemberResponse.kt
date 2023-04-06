package com.ssafy.challenmungs.data.remote.datasource.member

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.member.Member

data class MemberResponse(
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("loginId")
    val memberId: String,
    @SerializedName("type")
    val type: String,
) : DataToDomainMapper<Member> {

    override fun toDomainModel(): Member =
        Member(profile, name, memberId, type)
}
