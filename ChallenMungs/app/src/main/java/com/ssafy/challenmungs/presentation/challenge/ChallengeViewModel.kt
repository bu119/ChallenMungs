package com.ssafy.challenmungs.presentation.challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicHistory
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicToday
import com.ssafy.challenmungs.domain.entity.challenge.NotStartedChallengeDetail
import com.ssafy.challenmungs.domain.usecase.challenge.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val getChallengeListUseCase: GetChallengeListUseCase,
    private val getChallengeInfoUseCase: GetChallengeInfoUseCase,
    private val getBasicTodayUseCase: GetBasicTodayUseCase,
    private val getBasicHistoryUseCase: GetBasicHistoryUseCase,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val getChallengeParticipationFlagUseCase: GetChallengeParticipationFlagUseCase,
    private val requestParticipateUseCase: RequestParticipateUseCase,
    private val requestWithDrawUseCase: RequestWithDrawUseCase,
) : ViewModel() {

    private val _challengeList: MutableLiveData<List<Challenge>?> =
        MutableLiveData(arrayListOf())
    val challengeList: LiveData<List<Challenge>?> = _challengeList

    private val _notStartedChallengeDetail: MutableLiveData<NotStartedChallengeDetail?> =
        MutableLiveData()
    val notStartedChallengeDetail: LiveData<NotStartedChallengeDetail?> = _notStartedChallengeDetail

    private val _basicTodayList: MutableLiveData<List<ChallengeBasicToday>?> =
        MutableLiveData(null)
    val basicTodayList: LiveData<List<ChallengeBasicToday>?> = _basicTodayList

    private val _basicTodayHistory: MutableLiveData<List<ChallengeBasicHistory>?> =
        MutableLiveData(null)
    val basicTodayHistory: LiveData<List<ChallengeBasicHistory>?> = _basicTodayHistory

    fun initNotStartedChallengeDetail() {
        _notStartedChallengeDetail.value = null
    }

    fun setChallengeParticipationFlag(flag: Boolean) {
        _notStartedChallengeDetail.value?.let {
            it.isParticipated = flag
        }
    }

    fun getChallengeList(type: Int, searchValue: String? = null) = viewModelScope.launch {
        when (val value = getChallengeListUseCase(type, searchValue)) {
            is Resource.Success<List<Challenge>> -> _challengeList.value = value.data
            is Resource.Error -> Log.e(
                "getChallengeList",
                "getChallengeList: ${value.errorMessage}"
            )
        }
    }

    fun getChallengeInfo(challengeId: Int) = viewModelScope.launch {
        when (val value = getChallengeInfoUseCase(challengeId)) {
            is Resource.Success<NotStartedChallengeDetail> ->
                _notStartedChallengeDetail.value = value.data
            is Resource.Error -> Log.e(
                "getChallengeInfo",
                "getChallengeInfo: ${value.errorMessage}"
            )
        }
    }

    fun getBasicToday(challengeId: Int) = viewModelScope.launch {
        when (val value = getBasicTodayUseCase(challengeId)) {
            is Resource.Success<List<ChallengeBasicToday>> ->
                _basicTodayList.value = value.data
            is Resource.Error -> Log.e(
                "getBasicToday",
                "getBasicToday: ${value.errorMessage}"
            )
        }
    }

    fun getBasicHistory(challengeId: Int, targetMemberId: String) = viewModelScope.launch {
        when (val value = getBasicHistoryUseCase(challengeId, targetMemberId)) {
            is Resource.Success<List<ChallengeBasicHistory>> ->
                _basicTodayHistory.value = value.data
            is Resource.Error -> Log.e("getBasicHistory", "getBasicHistory: ${value.errorMessage}")
        }
    }

    fun getParticipants(challengeId: Int) = viewModelScope.launch {
        when(val value = getParticipantsUseCase(challengeId)){

        }
    }

    suspend fun getChallengeParticipationFlag(challengeId: Long) = viewModelScope.async {
        when (val value = getChallengeParticipationFlagUseCase(challengeId)) {
            is Resource.Success<Boolean> -> {
                _notStartedChallengeDetail.value?.let {
                    it.isParticipated = value.data
                }
                return@async true
            }
            is Resource.Error -> {
                Log.e(
                    "getChallengeParticipationFlag",
                    "getChallengeParticipationFlag: ${value.errorMessage}"
                )
                return@async false
            }
        }
    }.await()

    suspend fun requestParticipate(challengeId: Long, teamId: Int? = null) = viewModelScope.async {
        when (val value = requestParticipateUseCase(challengeId, teamId)) {
            is Resource.Success<String> -> return@async true
            is Resource.Error -> {
                Log.e("requestParticipate", "requestParticipate: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    suspend fun requestWithDraw(challengeId: Long) = viewModelScope.async {
        when (val value = requestWithDrawUseCase(challengeId)) {
            is Resource.Success<String> -> return@async true
            is Resource.Error -> {
                Log.e("requestWithDraw", "requestWithDraw: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}