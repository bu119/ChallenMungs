package com.ssafy.challenmungs.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.member.Auth

data class AuthResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("code")
    val code: String
) : DataToDomainMapper<Auth> {
    override fun toDomainModel(): Auth = Auth(result = result, code = code)
}