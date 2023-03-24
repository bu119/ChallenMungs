package com.ssafy.challenmungs.presentation.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel() {

    private val _profileImgUrl: MutableLiveData<String?> = MutableLiveData()
    val profileImgUrl: MutableLiveData<String?> = _profileImgUrl

    fun getInfo() {
        // 사용자 정보 가져오기
    }
}