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
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _address: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val address: LiveData<ArrayList<String>> = _address

    suspend fun createAccount() = viewModelScope.async {
        when (val value = createAccountUseCase()) {
            is Resource.Success<Account> -> {
                if (address.value == null)
                    _address.value = arrayListOf()

                _address.value!!.add(value.data.address)
                Log.d("createAccount", "createAccount: ${address.value}")
                return@async true
            }
            is Resource.Error -> {
                Log.e("createAccount", "createAccount: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}