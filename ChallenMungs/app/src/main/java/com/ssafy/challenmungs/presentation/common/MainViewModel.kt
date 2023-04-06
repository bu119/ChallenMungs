package com.ssafy.challenmungs.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _status: MutableLiveData<Int> = MutableLiveData(2)
    val status: LiveData<Int> = _status

    fun setStatus(status: Int) {
        _status.value = status
    }
}