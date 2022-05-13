package com.example.gastrozone.adapters

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

const val ENDPOINT_HOST = "37.9.170.36"
const val ENDPOINT_PORT = 1338

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://$ENDPOINT_HOST:$ENDPOINT_PORT")
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket;
    }
}