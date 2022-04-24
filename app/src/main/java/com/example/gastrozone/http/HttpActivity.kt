package com.example.gastrozone.http

import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.json.JSONObject
import org.json.JSONTokener


class HttpActivity : AppCompatActivity() {
    // Create OkHttp Client
    var client: OkHttpClient = OkHttpClient();
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
                .header("Authorization", auth)
                .post(empty)
                .build()
            val response = client.newCall(requestBuilder).execute()
            result = response.body?.string()
            val jsonObject = JSONTokener(result).nextValue() as JSONObject
            val token = jsonObject.getString("token")
            println(Token.token)
            Token.change_token(token)
            println(Token.token)

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
}
object  Token
{
    var token = ""
    fun change_token(new_token:String)
    {
        token = new_token
    }
}