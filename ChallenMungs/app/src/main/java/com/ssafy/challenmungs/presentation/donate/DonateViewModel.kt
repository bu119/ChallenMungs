package com.ssafy.challenmungs.presentation.donate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.Campaign
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard
import com.ssafy.challenmungs.domain.usecase.donate.GetBalanceUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignInfoUseCase
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import com.ssafy.challenmungs.domain.usecase.donate.RequestDonateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    private val getCampaignInfoUseCase: GetCampaignInfoUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val requestDonateUseCase: RequestDonateUseCase,
) : ViewModel() {

    private val _campaignList: MutableLiveData<List<CampaignCard>?> =
        MutableLiveData(listOf())
    val campaignList: LiveData<List<CampaignCard>?> = _campaignList

    private val _selectedCampaignId: MutableLiveData<Int> = MutableLiveData()
    val selectedCampaignId: LiveData<Int> = _selectedCampaignId

    private val _campaignInfo: MutableLiveData<Campaign?> = MutableLiveData()
    val campaignInfo: LiveData<Campaign?> = _campaignInfo

    private val _balancePiggyBank: MutableLiveData<Int> = MutableLiveData(0)
    val balancePiggyBank: LiveData<Int> = _balancePiggyBank

    fun initCampaignInfo() {
        _campaignInfo.value = null
    }

    fun setSelectedCampaignId(campaignId: Int) {
        _selectedCampaignId.value = campaignId
    }

    fun getCampaignList(type: String, sort: Int) = viewModelScope.launch {
        when (val value = getCampaignListUseCase(type, sort)) {
            is Resource.Success<List<CampaignCard>> -> _campaignList.value = value.data
            is Resource.Error -> Log.e("getCampaignList", "getCampaignList: ${value.errorMessage}")
        }
    }

    fun getCampaignInfo(campaignId: Int) = viewModelScope.launch {
        when (val value = getCampaignInfoUseCase(campaignId)) {
            is Resource.Success<Campaign> -> _campaignInfo.value = value.data
            is Resource.Error -> Log.e("getCampaignInfo", "getCampaignInfo: ${value.errorMessage}")
        }
    }

    fun getBalance(type: String) = viewModelScope.launch {
        when (val value = getBalanceUseCase(type)) {
            is Resource.Success<String> -> _balancePiggyBank.value = value.data.toInt()
            is Resource.Error -> Log.e("getBalance", "getBalance: ${value.errorMessage}")
        }
    }

    fun requestDonate(campaignId: Int, money: Int, memo: String) = viewModelScope.launch {
        when (val value = requestDonateUseCase(campaignId, money, memo)) {
            is Resource.Success<String> -> {}
            is Resource.Error -> Log.e("requestDonate", "requestDonate: ${value.errorMessage}")
        }
    }
}