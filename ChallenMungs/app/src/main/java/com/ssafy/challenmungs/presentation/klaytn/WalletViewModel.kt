package com.ssafy.challenmungs.presentation.klaytn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.klaytn.Account
import com.ssafy.challenmungs.domain.usecase.klaytn.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _address: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val address: LiveData<ArrayList<String>> = _address

    fun createAccount() = viewModelScope.launch {
        when (val value = createAccountUseCase()) {
            is Resource.Success<Account> -> {
                val arrayList = arrayListOf<String>()

                if (address.value!!.size == 1)
                    arrayList.add(address.value!![0])
                arrayList.add(value.data.address)

                _address.value = arrayList
            }
            is Resource.Error ->
                Log.e("createAccount", "createAccount: ${value.errorMessage}")
        }
    }
}