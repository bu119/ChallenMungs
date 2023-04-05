package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.ssafy.challenmungs.domain.entity.challenge.PanelRevertDataSend
import com.ssafy.challenmungs.domain.entity.challenge.PanelRevertSend
import com.ssafy.challenmungs.domain.entity.challenge.PanelStartDataSend
import com.ssafy.challenmungs.domain.entity.challenge.PanelStartSend
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor(
    private val client: OkHttpClient,
) : DefaultLifecycleObserver {

    private lateinit var webSocket: WebSocket
    private lateinit var request: Request
    private lateinit var messageListener: MessageListener

    private var isConnect = false
    private var connectNum = 0

    fun init(url: String, _messageListener: MessageListener) {
        messageListener = _messageListener
        request = Request.Builder()
            .url(url)
            .build()
    }

    fun connect() {
        if (isConnect) {
            Log.i(TAG, "web socket connected")
            return
        }
        webSocket = client.newWebSocket(request, createListener())
        isConnect = true
    }

    private fun createListener(): WebSocketListener = WebSocketListener(messageListener)

    fun reconnect() {
        if (connectNum <= MAX_NUM) {
            try {
                Thread.sleep(MILLIS.toLong())
                connect()
                connectNum++
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else {
            Log.i(
                TAG,
                "reconnect over $MAX_NUM,please check url or network"
            )
        }
    }

    fun isConnect(): Boolean = isConnect

    fun disconnect() {
        if (isConnect) {
            webSocket.cancel()
            val result = webSocket.close(1000, "The client actively closes the connection ")
            if (result) {
                Log.d(TAG, "close: success")
            } else {
                Log.d(TAG, "close: failed")
            }
            isConnect = false
        }
    }

    private fun sendMessage(text: String): Boolean = if (!isConnect) false else webSocket.send(text)

    fun startWalking(challengeId: Long, loginId: String): Boolean {
        val text = Gson().toJson(PanelStartSend("access", PanelStartDataSend(challengeId, loginId)))
        return sendMessage(text)
    }

    fun revertPanel(lat: Double, lng: Double, challengeId: Long, loginId: String): Boolean {
        Thread.sleep(MILLIS.toLong())
        val text =
            Gson().toJson(
                PanelRevertSend(
                    "signaling",
                    PanelRevertDataSend(lat, lng, challengeId, loginId)
                )
            )
        return sendMessage(text)
    }

    override fun onPause(owner: LifecycleOwner) {
        disconnect()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disconnect()
    }

    companion object {
        private val TAG = "WebSocketManager"
        private const val MAX_NUM = 5  // Maximum number of reconnections
        private const val MILLIS = 1000  // Reconnection interval, milliseconds
    }
}