package com.ssafy.challenmungs.presentation.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeInfo
import com.ssafy.challenmungs.domain.usecase.challenge.GetChallengeInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PanelPlayViewModel @Inject constructor(
    private val getChallengeInfo: GetChallengeInfoUseCase


) : ViewModel() {

    private var _challengeInfo: MutableLiveData<ChallengeInfo> = MutableLiveData()
    val challengeInfo: LiveData<ChallengeInfo> = _challengeInfo
}