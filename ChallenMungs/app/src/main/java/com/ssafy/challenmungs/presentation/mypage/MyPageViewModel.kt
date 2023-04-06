package com.ssafy.challenmungs.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.usecase.mypage.GetMyWalletBalanceUseCase
import com.ssafy.challenmungs.domain.usecase.mypage.GetTotalDonateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getTotalDonateUseCase: GetTotalDonateUseCase,
    private val getMyWalletBalanceUseCase: GetMyWalletBalanceUseCase
) : ViewModel() {

    private val _totalDonate: MutableLiveData<String?> = MutableLiveData()
    val totalDonate: LiveData<String?> = _totalDonate

    private val _myWalletBalance: MutableLiveData<String?> = MutableLiveData()
    val myWalletBalance: LiveData<String?> = _myWalletBalance

    private val _heartTemperature: MutableLiveData<Int> = MutableLiveData(0)
    val heartTemperature: LiveData<Int> = _heartTemperature

    private val _piggyBankBalance: MutableLiveData<String?> = MutableLiveData()
    val piggyBankBalance: LiveData<String?> = _piggyBankBalance

    suspend fun getTotalDonate() = viewModelScope.async {
        when (val value = getTotalDonateUseCase()) {
            is Resource.Success<String> -> {
                _totalDonate.value = value.data
                _heartTemperature.value = ((value.data.toDouble() / 1000) * 100).toInt()
                return@async true
            }
            is Resource.Error -> {
                Log.e("getTotalDonate", "getTotalDonate: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    suspend fun getMyWalletBalance(type: String) = viewModelScope.async {
        when (val value = getMyWalletBalanceUseCase.invoke(type)) {
            is Resource.Success<String> -> {
                if (type == "w")
                    _myWalletBalance.value = value.data
                else
                    _piggyBankBalance.value = value.data

                return@async true
            }
            is Resource.Error -> {
                Log.e("getTotalDonate", "getTotalDonate: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}