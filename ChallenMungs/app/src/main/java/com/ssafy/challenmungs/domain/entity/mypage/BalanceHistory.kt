package com.ssafy.challenmungs.domain.entity.mypage

data class BalanceHistory(
    val date: String,
    val items: List<BalanceDetail>
)

data class BalanceDetail(
    val title: String,
    val money: Int,
    val time: String,
    val totalMoney: Int,
    val imgRes: Int
)