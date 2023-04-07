package com.ssafy.challenmungs.domain.entity.challenge

// 보내는 부분
// 산책시작하기
data class PanelStartSend(
    val event: String,
    val data: PanelStartDataSend,
)

data class PanelStartDataSend(
    val challengeId: Long,
    val loginId: String,
)

// 누군가가 판넬을 뒤집음
data class PanelRevertSend(
    val event: String,
    val data: PanelRevertDataSend,
)

data class PanelRevertDataSend(
    val lat: Double,
    val lng: Double,
    val challengeId: Long,
    val loginId: String,
)

// 받는 부분
// 누군가가 판넬을 뒤집음
data class PanelRevertResponse(
    val code: String,
    val value: PanelRevertDataResponse,
)

data class PanelRevertDataResponse(
    val indexC: Int,
    val indexR: Int,
    val rankInfo: ArrayList<RankDetail>,
    val teamId: Int,
    val teamDraw: Boolean
)