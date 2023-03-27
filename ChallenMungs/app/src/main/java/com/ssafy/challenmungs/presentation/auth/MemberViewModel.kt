package com.ssafy.challenmungs.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.member.Member
import com.ssafy.challenmungs.domain.usecase.member.GetMemberInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val getMemberInfoUseCase: GetMemberInfoUseCase
) : ViewModel() {

    private val _memberInfo: MutableLiveData<Member?> = MutableLiveData()
    val memberInfo: LiveData<Member?> = _memberInfo

    suspend fun getMemberInfo() = viewModelScope.async {
        when (val value = getMemberInfoUseCase()) {
            is Resource.Success<Member> -> {
                _memberInfo.value = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e("getMemberInfo", "getMemberInfo: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()
}