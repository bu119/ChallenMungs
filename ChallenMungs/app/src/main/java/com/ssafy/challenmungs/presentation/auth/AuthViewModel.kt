package com.ssafy.challenmungs.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Auth
import com.ssafy.challenmungs.domain.usecase.auth.JoinUseCase
import com.ssafy.challenmungs.domain.usecase.auth.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val joinUseCase: JoinUseCase
) : ViewModel() {

    private val _accessToken: MutableLiveData<String?> = MutableLiveData()
    val accessToken: LiveData<String?> = _accessToken

    private val _isNewMember: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNewMember: LiveData<Boolean> = _isNewMember

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }

    fun requestLogin(body: RequestBody) = viewModelScope.launch {
        when (val value = logInUseCase(body)) {
            is Resource.Success<Auth> -> {
                if (value.data.code == "member")
                    ApplicationClass.preferences.accessToken = value.data.result
            }
            is Resource.Error -> {
                when (value.errorMessage) {
                    "423" -> _isNewMember.value = true
                    else -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
                }
            }
        }
    }

    fun requestJoin(name: String) = viewModelScope.launch {
        when (val value = joinUseCase(name, accessToken.value!!)) {
            is Resource.Success<String> -> ApplicationClass.preferences.accessToken = value.data
            is Resource.Error -> Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
        }
    }
}