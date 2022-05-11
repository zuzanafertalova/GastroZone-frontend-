package com.example.gastrozone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.dialogs.DialogDeleteAccount
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.chat.*
import kotlinx.android.synthetic.main.fragment_set_username.*
import org.json.JSONObject
import org.json.JSONTokener

class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)
        val response = HttpActivity()
        val nieco = response.connectionWebSockt("10.0.2.2", 8080)
        btnChat()


    }

    private fun btnChat() {
        button_chat.setOnClickListener {
            val massage: String = tvChat.text.toString()
            val response = HttpActivity()
            val nieco = response.send(massage)
        }
    }

}