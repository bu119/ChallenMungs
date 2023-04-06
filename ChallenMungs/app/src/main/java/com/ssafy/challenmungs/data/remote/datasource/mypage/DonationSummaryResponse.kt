package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.mypage.DonationSummary

data class DonationSummaryResponse(
    val donateCount: Int,
    val sumYearMoney: Int,
    val sumTotalMoney: Int,
) : DataToDomainMapper<DonationSummary> {

    override fun toDomainModel(): DonationSummary = DonationSummary(
        donateCount = donateCount,
        sumYearMoney = sumYearMoney,
        sumTotalMoney = sumTotalMoney
    )
}