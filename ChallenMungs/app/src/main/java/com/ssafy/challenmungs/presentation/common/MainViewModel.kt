package com.ssafy.challenmungs.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _fullScreenMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val fullScreenMode: LiveData<Boolean> = _fullScreenMode

    fun setFullScreenMode(flag: Boolean) {
        _fullScreenMode.value = flag
    }
}