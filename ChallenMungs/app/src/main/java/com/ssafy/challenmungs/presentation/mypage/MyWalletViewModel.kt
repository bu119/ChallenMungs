package com.ssafy.challenmungs.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.usecase.mypage.GetMyWalletHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MyWalletViewModel @Inject constructor(
    private val getMyWalletHistoryUseCase: GetMyWalletHistoryUseCase,
) : ViewModel() {

    private val _walletHistory: MutableLiveData<List<BalanceHistory>?> = MutableLiveData()
    val walletHistory: LiveData<List<BalanceHistory>?> = _walletHistory

    suspend fun getMyWalletHistory() = viewModelScope.async {
        when (val value = getMyWalletHistoryUseCase()) {
            is Resource.Success<List<BalanceHistory>> -> {
                _walletHistory.value = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e("getTotalDonate", "getTotalDonate: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}