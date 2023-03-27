package com.ssafy.challenmungs.data.remote.datasource.common

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper

data class ResultResponse(
    @SerializedName("result")
    val result: String
) : DataToDomainMapper<String> {

    override fun toDomainModel(): String = result
}