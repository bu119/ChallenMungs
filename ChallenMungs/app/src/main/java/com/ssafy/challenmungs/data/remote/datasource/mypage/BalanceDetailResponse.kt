package com.ssafy.challenmungs.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.mypage.BalanceDetail

data class BalanceDetailResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("money")
    val money: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("totalMoney")
    val totalMoney: Int,
) : DataToDomainMapper<BalanceDetail> {

    override fun toDomainModel(): BalanceDetail =
        BalanceDetail(
            title = title, money = money, time = time, totalMoney = totalMoney, when (title) {
                "충전" -> R.drawable.ic_wallet_charge
                "일반 챌린지 참여", "일반 챌린지 보상" -> R.drawable.ic_wallet_normal_challenge
                "특별 챌린지 참여", "특별 챌린지 보상" -> R.drawable.ic_history_challenge
                "특별 챌린지 환불", "일반 챌린지 환불" -> R.drawable.ic_wallet_exchange
                "출금" -> R.drawable.ic_history_withdraw
                else -> R.drawable.ic_history_donate
            }
        )
}