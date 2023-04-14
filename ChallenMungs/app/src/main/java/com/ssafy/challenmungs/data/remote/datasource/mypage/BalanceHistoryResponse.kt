package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory

data class BalanceHistoryResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("items")
    val details: List<BalanceDetailResponse>,
) : DataToDomainMapper<BalanceHistory> {

    override fun toDomainModel(): BalanceHistory =
        BalanceHistory(date, details.map { it.toDomainModel() })
}