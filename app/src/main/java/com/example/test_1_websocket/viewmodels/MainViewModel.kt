package com.example.test_1_websocket.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private var client: OkHttpClient? = null

    var mResponseSocket = MutableLiveData<String>()
    var connect =  MutableLiveData<Boolean>()


    init {
        Log.e("AAA", "VM created")
        client = OkHttpClient()
    }


    open inner class EchoWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            webSocket.send("{\"method\": \"subscribe\",\"ch\": \"ticker/1s/batch\", \"params\": {\"symbols\": [\"ETHBTC\",\"BTCUSD\"]},\"id\": 123}")
            connect.value = true
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            if (connect.value == false) {
                webSocket.send("{\"method\": \"unsubscribe\",\"ch\": \"ticker/1s/batch\", \"params\": {\"symbols\": [\"ETHBTC\",\"BTCUSD\"]},\"id\": 123}")
            }
            output("----> Receiving : $text \n")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            output("----> Receiving bytes : " + bytes.hex() +"\n")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(1000, null)
            output("----> Closing : $code  /  $reason \n")
            connect.value = false
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            output("----> Error : " + t.message +"\n")
            connect.value = false
        }
    }


    private fun output(txt: String) {
        mResponseSocket.value = txt
    }


    fun start() {
        if (connect.value == false) {
            val request = Request.Builder().url("wss://api.demo.hitbtc.com/api/3/ws/public").build()
            val listener = EchoWebSocketListener()
            client?.newWebSocket(request, listener) // val wss =
        }
    }



    override fun onCleared() {
        super.onCleared()
        Log.e("AAA", "VM onCleared")
    }
}