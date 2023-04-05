package com.ssafy.challenmungs.presentation.challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.domain.entity.challenge.NotStartedChallengeDetail
import com.ssafy.challenmungs.domain.usecase.challenge.GetChallengeInfoUseCase
import com.ssafy.challenmungs.domain.usecase.challenge.GetChallengeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val getChallengeListUseCase: GetChallengeListUseCase,
    private val getChallengeInfoUseCase: GetChallengeInfoUseCase
) : ViewModel() {

    private val _challengeList: MutableLiveData<List<Challenge>?> =
        MutableLiveData(arrayListOf())
    val challengeList: LiveData<List<Challenge>?> = _challengeList

    private val _notStartedChallengeDetail: MutableLiveData<NotStartedChallengeDetail?> =
        MutableLiveData()
    val notStartedChallengeDetail: LiveData<NotStartedChallengeDetail?> = _notStartedChallengeDetail

    fun initNotStartedChallengeDetail() {
        _notStartedChallengeDetail.value = null
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
            is Resource.Success<NotStartedChallengeDetail> -> _notStartedChallengeDetail.value =
                value.data
            is Resource.Error -> Log.e(
                "getChallengeInfo",
                "getChallengeInfo: ${value.errorMessage}"
            )
        }
    }
}