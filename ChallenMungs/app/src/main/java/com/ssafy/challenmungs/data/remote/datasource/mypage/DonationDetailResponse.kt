package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.mypage.DonationDetail

data class DonationDetailResponse(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("money")
    val money: Int,
    @SerializedName("shelter")
    val shelter: String,
    @SerializedName("donateDay")
    val donateDay: String
) : DataToDomainMapper<DonationDetail> {

    override fun toDomainModel(): DonationDetail =
        DonationDetail(userName = userName, money = money, shelter = shelter, donateDay = donateDay)
}