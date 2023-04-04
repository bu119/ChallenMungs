package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

interface MessageListener {

    fun onConnectSuccess()
    fun onConnectFailed()
    fun onClose()
    fun onMessage(text: String?)
}