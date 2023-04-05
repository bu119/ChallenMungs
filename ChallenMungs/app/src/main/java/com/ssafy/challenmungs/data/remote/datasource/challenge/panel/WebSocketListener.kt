package com.ssafy.challenmungs.data.remote.datasource.challenge.panel

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketListener(private val messageListener: MessageListener) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        if (response.code == 101) {
            messageListener.onConnectSuccess()
        } else {
            messageListener.onConnectFailed()
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        messageListener.onMessage(text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        messageListener.onClose()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        if (response != null) {
            Log.i(
                TAG,
                "connect failed：" + response.message
            )
        }
        Log.i(
            TAG,
            "connect failed throwable：" + t.message
        )
        messageListener.onConnectFailed()
    }

    companion object {
        private const val TAG = "WebSocketListener"
    }
}