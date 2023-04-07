package com.ssafy.challenmungs.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.common.util.extractIdFromJWT
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.domain.entity.mypage.DonationDetail
import com.ssafy.challenmungs.domain.entity.mypage.DonationSummary
import com.ssafy.challenmungs.domain.usecase.mypage.GetDonationDetailUseCase
import com.ssafy.challenmungs.domain.usecase.mypage.GetDonationHistoryUseCase
import com.ssafy.challenmungs.domain.usecase.mypage.GetDonationSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DonationHistoryViewModel @Inject constructor(
    private val getDonationHistoryUseCase: GetDonationHistoryUseCase,
    private val getDonationSummaryUseCase: GetDonationSummaryUseCase,
    private val getDonationDetailUseCase: GetDonationDetailUseCase
) : ViewModel() {

    private val _donationHistory: MutableLiveData<List<BalanceHistory>?> = MutableLiveData()
    val donationHistory: LiveData<List<BalanceHistory>?> = _donationHistory

    private val _donationSummary: MutableLiveData<DonationSummary?> = MutableLiveData()
    val donationSummary: LiveData<DonationSummary?> = _donationSummary

    private val _donationDetail: MutableLiveData<DonationDetail?> = MutableLiveData()
    val donationDetail: LiveData<DonationDetail?> = _donationDetail

    private val _selectedYear: MutableLiveData<Int> = MutableLiveData(0)
    val selectedYear: LiveData<Int> = _selectedYear

    fun setSelectedYear(selectedYear: Int) {
        _selectedYear.value = selectedYear
    }

    suspend fun getDonationHistory() = viewModelScope.async {
        val accessToken = ApplicationClass.preferences.accessToken

        accessToken?.let { extractIdFromJWT(it) }?.let { loginId ->
            when (val value = getDonationHistoryUseCase(loginId, _selectedYear.value!!)) {
                is Resource.Success<List<BalanceHistory>> -> {
                    _donationHistory.value = value.data
                    return@async true
                }
                is Resource.Error -> {
                    Log.e("getDonationHistory", "getDonationHistory: ${value.errorMessage}")
                    return@async false
                }
            }
        }
    }.await()

    suspend fun getDonationSummary() = viewModelScope.async {
        val accessToken = ApplicationClass.preferences.accessToken

        accessToken?.let { extractIdFromJWT(it) }?.let { loginId ->
            when (val value = getDonationSummaryUseCase(loginId, _selectedYear.value!!)) {
                is Resource.Success<DonationSummary> -> {
                    _donationSummary.value = value.data
                    return@async true
                }
                is Resource.Error -> {
                    Log.e("getDonationSummary", "getDonationSummary: ${value.errorMessage}")
                    return@async false
                }
            }
        }
    }.await()

    suspend fun getDonationDetail(donationId: Int) = viewModelScope.async {
        when (val value = getDonationDetailUseCase(donationId)) {
            is Resource.Success<DonationDetail> -> {
                _donationDetail.value = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e("getDonationDetail", "getDonationDetail: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}