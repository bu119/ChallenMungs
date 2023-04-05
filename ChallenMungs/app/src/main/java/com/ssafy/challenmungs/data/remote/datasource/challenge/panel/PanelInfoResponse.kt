package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail

data class PanelInfoResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("mapCoordinate")
    val mapCoordinate: ArrayList<ArrayList<ArrayList<Location>>>,
    @SerializedName("mapInfo")
    val mapInfo: ArrayList<ArrayList<Int>>,
    @SerializedName("entryFee")
    val fee: Int,
    @SerializedName("centerLat")
    val centerLat: Double,
    @SerializedName("centerLng")
    val centerLng: Double,
    @SerializedName("gameType")
    val gameType: Int,
    @SerializedName("rankInfo")
    val rankInfo: ArrayList<RankDetailResponse>,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("teamDraw")
    val teamDraw: Boolean,
) : DataToDomainMapper<ChallengeInfo> {

    override fun toDomainModel(): ChallengeInfo =
        ChallengeInfo(
            title = title,
            category = "판넬 뒤집기",
            startDate = startDate,
            mapCoordinate = mapCoordinate,
            endDate = endDate,
            fee = fee.toString(),
            centerLat = centerLat,
            centerLng = centerLng,
            totalFee = (fee * rankInfo.size).toString(),
            type = when (gameType) {
                1 -> "개인전"
                else -> "팀전"
            },
            currentRank = ArrayList(
                rankInfo.map {
                    RankDetail(
                        it.profile,
                        it.name,
                        it.userId,
                        it.count,
                        it.teamRank,
                        it.indiRank,
                        it.teamId,
                        when (gameType) {
                            1 -> {
                                when (it.indiRank) {
                                    1 -> R.drawable.ic_gold_crown
                                    2 -> R.drawable.ic_silver_crown
                                    3 -> R.drawable.ic_bronze_crown
                                    else -> null
                                }
                            }
                            else -> {
                                when (it.indiRank) {
                                    1 -> R.drawable.ic_gold_crown
                                    else -> R.drawable.ic_silver_crown
                                }
                            }
                        }
                    )
                }),
            mapInfo = mapInfo,
            teamDraw = teamDraw
        )
}

data class RankDetailResponse(
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("loginId")
    val userId: String,
    @SerializedName("point")
    val count: Int,
    @SerializedName("teamRank")
    val teamRank: Int,
    @SerializedName("indiRank")
    val indiRank: Int,
    @SerializedName("teamId")
    val teamId: Int,
)

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)