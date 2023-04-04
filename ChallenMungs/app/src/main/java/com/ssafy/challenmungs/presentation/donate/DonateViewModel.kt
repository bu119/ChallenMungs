package com.ssafy.challenmungs.presentation.donate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard
import com.ssafy.challenmungs.domain.usecase.donate.GetCampaignListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : ViewModel() {

    private val _campaignList: MutableLiveData<List<CampaignCard>?> =
        MutableLiveData(listOf())
    val campaignList: LiveData<List<CampaignCard>?> = _campaignList

    fun getCampaignList(type: String, sort: Int) = viewModelScope.launch {
        when (val value = getCampaignListUseCase(type, sort)) {
            is Resource.Success<List<CampaignCard>> -> _campaignList.value = value.data
            is Resource.Error -> Log.e("getCampaignList", "getCampaignList: ${value.errorMessage}")
        }
    }
}