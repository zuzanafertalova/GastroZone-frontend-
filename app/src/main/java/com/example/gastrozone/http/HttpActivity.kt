package com.example.gastrozone.http

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File


class HttpActivity : Runnable {
    // Create OkHttp Client
    private val client: OkHttpClient = OkHttpClient()

    override fun run() {
    }

    fun ExecGETRequest(uri: String, authTokenLegacy: String? = "ReplaceMe"): String? {


        val authToken : String = this.get_token()

        val requestBuilder = Request.Builder()
            .url(uri)
            .addHeader("X-Access-Token", authToken)
            .addHeader("Connection","close")
            .get()
            .build()

        val res = client.newCall(requestBuilder).execute()


        return res.body?.string()
    }
    fun ExecPUTRequest(uri: String, authTokenLegacy: String? = "ReplaceMe", putJson: String): String? {

        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        val authToken : String = this.get_token()

        val requestBuilder = Request.Builder()
            .url(uri)
            .addHeader("X-Access-Token", authToken)
            .addHeader("Connection","close")
            .header("Content-Type", "application/json")
            .put(putJson.toRequestBody(mediaTypeJson))
            .build()

        val res = client.newCall(requestBuilder).execute()


        return res.body?.string()
    }
    fun connectionWebSockt(hostName:String,port:Int){
        val httpClient = OkHttpClient.Builder()
            .pingInterval(40, java.util.concurrent.TimeUnit.SECONDS)
            .build()
        val webSocketUrl = "ws://${hostName}:${port}"
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()
        httpClient.newWebSocket(request, object:WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                //Websocket connection establishment
                Nieco.setWebSocket(webSocket)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                println(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
            }
        })
    }
    fun send(message: String?) {
        get_WebSocket()?.send(message!!)
    }

    fun ExecPOSTImage(uri: String, image: File, imageName: String): String? {
        val mediaTypeimage = "image/png".toMediaType()
        val authToken : String = this.get_token()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", imageName, RequestBody.create(mediaTypeimage, image))
            .build()

        val requestBuilder = Request.Builder()
            .url(uri)
            .addHeader("X-Access-Token", authToken)
            .addHeader("Connection","close")
            .post(requestBody)
            .build()
        val res = client.newCall(requestBuilder).execute()


        return res.body?.string()

    }


    fun ExecDeleteRequest(uri: String, payloadData: String="{}"): String {
        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        var authToken : String = this.get_token()

        println(payloadData)

        val requestBuilder = Request.Builder()
            .url(uri)
            .addHeader("X-Access-Token", authToken)
            .addHeader("Connection","keepalive")
            .header("Content-Type", "application/json")
            .delete(payloadData.toRequestBody(mediaTypeJson))
            .build()

        val res = client.newCall(requestBuilder).execute()


        return res.body.toString()
    }


    fun register(sUrl: String, jsonPost: String): String? {
        var result: String? = null
        try {

            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val requestBuilder = Request.Builder()
                .url(sUrl)
                .header("Content-Type", "application/json")
                .post(jsonPost.toRequestBody(mediaTypeJson))
                .build()
            val response = client.newCall(requestBuilder).execute()
            result = response.body?.string()

        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }

    fun login(sUrl: String, auth: String): String? {
        var result: String? = null
        try {
            val empty: RequestBody = EMPTY_REQUEST
            val requestBuilder = Request.Builder()
                .url(sUrl)
                .addHeader("Authorization", auth)
                .addHeader("Connection","close")
                .post(empty)
                .build()
            client.newCall(requestBuilder).execute().use { response ->
                if (response.isSuccessful)
                    result = response.body?.string()
                    if(result == null)
                        return "zle meno alebo heslo"
                    val jsonObject = JSONTokener(result).nextValue() as JSONObject
                    val token = jsonObject.getString("token")
                    val user_type = jsonObject.getString("user_type")
                    println("Got following payload: ${response.body}")
                    Token.change_token(token)
                    println(Token.token)
                    Token.user(user_type)
                    println(Token.user_type)


            }




            if (result?.isEmpty() == true) {
                println("Got invalid response")
            }



        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }

    fun filter(sUrl: String): String? {
        var result: String? = null
        try {
            val requestBuilder = Request.Builder()
                .url(sUrl)
                .header("X-Access-Token", Token.token)
                .build()
            val response = client.newCall(requestBuilder).execute()
            result = response.body?.string()
            val jsonObject = JSONTokener(result).nextValue() as JSONObject
            val filter = jsonObject.getString("list_of_companies")
            println(filter)

        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }

    fun get_token() : String {
        return Token.token
    }
    fun get_WebSocket() : WebSocket? {
        return Nieco.mWebSocket
    }
}
object Nieco{
    var mWebSocket : WebSocket? = null
    fun setWebSocket(newWebSocket : WebSocket){
        mWebSocket = newWebSocket

    }
}

object Token {
    var token = ""
    var user_type = ""
    fun change_token(new_token: String) {
        token = new_token
    }

    fun user(new_user: String) {
        user_type = new_user
    }
}