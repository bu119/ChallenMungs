package com.ssafy.challenmungs.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Auth
import com.ssafy.challenmungs.domain.usecase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val joinUseCase: JoinUseCase,
    private val setWalletUseCase: SetWalletUseCase,
    private val requestShelterJoinUseCase: RequestShelterJoinUseCase,
    private val requestInviteCodeUseCase: RequestInviteCodeUseCase,
) : ViewModel() {

    private val _accessToken: MutableLiveData<String?> = MutableLiveData()
    val accessToken: LiveData<String?> = _accessToken

    private val _authType: MutableLiveData<String> = MutableLiveData("")
    val authType: LiveData<String> = _authType

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }

    fun requestLogin(body: RequestBody) = viewModelScope.launch {
        when (val value = logInUseCase(body)) {
            is Resource.Success<Auth> -> {
                if (value.data.code == "member") {
                    ApplicationClass.preferences.accessToken = value.data.result
                    _authType.value = "member"
                }
            }
            is Resource.Error -> {
                when (value.errorMessage) {
                    "423" -> _authType.value = "new"
                    else -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
                }
            }
        }
    }

    fun requestJoin(name: String) = viewModelScope.launch {
        when (val value = joinUseCase(name, accessToken.value!!)) {
            is Resource.Success<String> -> {
                ApplicationClass.preferences.accessToken = value.data
                _authType.value = "member"
            }
            is Resource.Error ->
                Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
        }
    }

    suspend fun requestShelterJoin(
        shelterName: String,
        inviteCode: String,
        memberId: String,
        password: String
    ) = viewModelScope.async {
        when (val value = requestShelterJoinUseCase(shelterName, inviteCode, memberId, password)) {
            is Resource.Success<String> -> {
                ApplicationClass.preferences.accessToken = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e("requestShelterJoin", "requestShelterJoin: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    suspend fun setWallet(memberId: String, piggyBank: String, wallet: String) =
        viewModelScope.async {
            when (val value = setWalletUseCase(memberId, piggyBank, wallet)) {
                is Resource.Success<String> -> {
                    return@async true
                }
                is Resource.Error -> {
                    Log.e("setWallet", "setWallet: ${value.errorMessage}")
                    return@async false
                }
            }
        }.await()

    fun requestInviteCode(shelterName: String, email: String) = viewModelScope.launch {
        when (val value = requestInviteCodeUseCase(shelterName, email)) {
            is Resource.Success<String> -> {}
            is Resource.Error -> Log.e(
                "requestInviteCode",
                "requestInviteCode: ${value.errorMessage}"
            )
        }
    }
}