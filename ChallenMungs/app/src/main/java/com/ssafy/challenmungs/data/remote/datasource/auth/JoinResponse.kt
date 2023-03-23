package com.ssafy.challenmungs.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper

data class JoinResponse(
    @SerializedName("result")
    val jwtToken: String
) : DataToDomainMapper<String> {

    override fun toDomainModel(): String = jwtToken
}
