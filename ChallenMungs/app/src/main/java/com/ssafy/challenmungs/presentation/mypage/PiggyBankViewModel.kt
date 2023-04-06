package com.ssafy.challenmungs.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.usecase.mypage.GetPiggyBankHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class PiggyBankViewModel @Inject constructor(
    private val getPiggyBankHistoryUseCase: GetPiggyBankHistoryUseCase,
) : ViewModel() {

    private val _piggyBankHistory: MutableLiveData<List<BalanceHistory>?> = MutableLiveData()
    val piggyBankHistory: LiveData<List<BalanceHistory>?> = _piggyBankHistory

    suspend fun getMyWalletHistory() = viewModelScope.async {
        when (val value = getPiggyBankHistoryUseCase()) {
            is Resource.Success<List<BalanceHistory>> -> {
                _piggyBankHistory.value = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e("getMyWalletHistory", "getMyWalletHistory: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}