package com.ssafy.challenmungs.presentation.challenge.panel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.usecase.challenge.CreatePanelChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class PanelCreateViewModel @Inject constructor(
    private val createPanelChallengeUseCase: CreatePanelChallengeUseCase
) : ViewModel() {

    private var _isAreaSetting: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAreaSetting: LiveData<Boolean> = _isAreaSetting

    private var _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> = _title

    private var _startDate: MutableLiveData<String> = MutableLiveData("")
    val startDate: LiveData<String> = _startDate

    private var _endDate: MutableLiveData<String> = MutableLiveData("")
    val endDate: LiveData<String> = _endDate

    private var _maxParticipantCount: MutableLiveData<Int> = MutableLiveData(4)
    val maxParticipantCount: LiveData<Int> = _maxParticipantCount

    private var _gameType: MutableLiveData<Int> = MutableLiveData(1)
    val gameType: LiveData<Int> = _gameType

    private var _entryFee: MutableLiveData<Int> = MutableLiveData(10)
    val entryFee: LiveData<Int> = _entryFee

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setIsAreaSetting(isAreaSetting: Boolean) {
        _isAreaSetting.value = isAreaSetting
    }

    fun setStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun setEndDate(endDate: String) {
        _endDate.value = endDate
    }

    fun setGameType(gameType: Int) {
        _gameType.value = gameType
    }

    fun setEntryFee(entryFee: Int) {
        _entryFee.value = entryFee
    }

    fun setMaxParticipantCount(maxParticipantCount: Int) {
        _maxParticipantCount.value = maxParticipantCount
    }

    suspend fun createPanelChallenge(
        centerLat: Double,
        centerLng: Double,
        mapSize: Double,
        cellSize: Double,
    ): Boolean = viewModelScope.async {
        when (val value = createPanelChallengeUseCase(
            _title.value ?: "",
            _startDate.value ?: "",
            _endDate.value ?: "",
            _maxParticipantCount.value ?: 4,
            _gameType.value ?: 1,
            _entryFee.value ?: 10,
            centerLat,
            centerLng,
            mapSize,
            cellSize
        )) {
            is Resource.Success<String> -> {
                return@async true
            }
            is Resource.Error -> {
                Log.e("PanelCreateViewModel", "createPanelChallenge: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}