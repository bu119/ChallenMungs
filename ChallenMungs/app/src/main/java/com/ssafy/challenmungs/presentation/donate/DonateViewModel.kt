package com.ssafy.challenmungs.presentation.donate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard
import com.ssafy.challenmungs.domain.usecase.donate.GetBalanceUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import com.ssafy.challenmungs.domain.usecase.donate.RequestDonateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val requestDonateUseCase: RequestDonateUseCase,
) : ViewModel() {

    private val _campaignList: MutableLiveData<List<CampaignCard>?> =
        MutableLiveData(listOf())
    val campaignList: LiveData<List<CampaignCard>?> = _campaignList

    private val _balancePiggyBank: MutableLiveData<Int> = MutableLiveData(0)
    val balancePiggyBank: LiveData<Int> = _balancePiggyBank

    fun getCampaignList(type: String, sort: Int) = viewModelScope.launch {
        when (val value = getCampaignListUseCase(type, sort)) {
            is Resource.Success<List<CampaignCard>> -> _campaignList.value = value.data
            is Resource.Error -> Log.e("getCampaignList", "getCampaignList: ${value.errorMessage}")
        }
    }

    fun getBalance(type: String) = viewModelScope.launch {
        when (val value = getBalanceUseCase(type)) {
            is Resource.Success<String> -> _balancePiggyBank.value = value.data.toInt()
            is Resource.Error -> Log.e("getBalance", "getBalance: ${value.errorMessage}")
        }
    }

    fun requestDonate(money: Int, memo: String) = viewModelScope.launch {
        when (val value = requestDonateUseCase(12, money, memo)) {
            is Resource.Success<String> -> {}
            is Resource.Error -> Log.e("requestDonate", "requestDonate: ${value.errorMessage}")
        }
    }
}