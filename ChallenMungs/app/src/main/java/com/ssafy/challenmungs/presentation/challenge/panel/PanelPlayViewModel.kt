package com.ssafy.challenmungs.presentation.challenge.panel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail
import com.ssafy.challenmungs.domain.usecase.challenge.GetPanelInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PanelPlayViewModel @Inject constructor(
    private val getPanelInfoUseCase: GetPanelInfoUseCase
) : ViewModel() {

    private var _challengeInfo: MutableLiveData<ChallengeInfo?> = MutableLiveData()
    val challengeInfo: LiveData<ChallengeInfo?> = _challengeInfo

    private var _center: MutableLiveData<LatLng?> = MutableLiveData()
    val center: LiveData<LatLng?> = _center

    private var _rankInfo: MutableLiveData<ArrayList<RankDetail>?> = MutableLiveData()
    val rankInfo: LiveData<ArrayList<RankDetail>?> = _rankInfo

    private var _currentPosition: MutableLiveData<LatLng?> = MutableLiveData()
    val currentPosition: LiveData<LatLng?> = _currentPosition

    private var _userId: MutableLiveData<String?> = MutableLiveData()
    val userId: LiveData<String?> = _userId

    private var _myProfileImg: MutableLiveData<String?> = MutableLiveData()
    val myProfileImg: LiveData<String?> = _myProfileImg

    private var _myRank: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val myRank: LiveData<Pair<Int, Int>> = _myRank

    private var _mapInfo: MutableLiveData<ArrayList<ArrayList<Int>>> = MutableLiveData()
    val mapInfo: LiveData<ArrayList<ArrayList<Int>>> = _mapInfo

    private var _sumPoint: MutableLiveData<IntArray> = MutableLiveData(IntArray(7))
    val sumPoint: LiveData<IntArray> = _sumPoint

    suspend fun getPanelInfo(challengeId: Long) = viewModelScope.async {
        when (val value = getPanelInfoUseCase(challengeId)) {
            is Resource.Success<ChallengeInfo> -> {
                _challengeInfo.value = value.data
                _mapInfo.value = value.data.mapInfo
                _center.value = LatLng(value.data.centerLat, value.data.centerLng)

                setMyInfo(_challengeInfo.value?.currentRank)
                return@async true
            }
            is Resource.Error -> {
                Log.e("PanelPlayViewModel", "getPanelInfo: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    private fun setMyInfo(info: ArrayList<RankDetail>?) {
        var myTeamId = 0
        var myRank = 0
        val isTeamPlay = "팀전" == _challengeInfo.value?.type

        info?.forEach { rankDetail ->
            _sumPoint.value?.set(rankDetail.teamId, rankDetail.point)

            if (rankDetail.loginId == _userId.value) {
                _myProfileImg.value = rankDetail.profile
                myTeamId = rankDetail.teamId

                myRank = if (isTeamPlay) {
                    rankDetail.teamRank
                } else {
                    rankDetail.indiRank
                }
            }
        }

        _myRank.value = _sumPoint.value?.let { Pair(myRank, it[myTeamId]) }
    }

    fun setRankInfo(rankInfo: ArrayList<RankDetail>) {
        viewModelScope.launch(Dispatchers.Main) {
            _rankInfo.value = rankInfo
            _challengeInfo.value?.currentRank = rankInfo

            setMyInfo(_rankInfo.value)
        }
    }

    fun setUserId(userId: String?) {
        _userId.value = userId
    }

    fun setCurrentPosition(position: LatLng?) {
        _currentPosition.value = position
    }

    fun setMapInfo(mapInfo: ArrayList<ArrayList<Int>>) {
        _mapInfo.value = mapInfo
    }
}