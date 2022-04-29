package com.example.gastrozone.http

import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.sql.SQLOutput
import kotlin.reflect.typeOf


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