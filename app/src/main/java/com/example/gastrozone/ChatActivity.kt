package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_set_username.*

class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val response = HttpActivity()
        val nieco = response.connectionWebSockt("10.0.2.2", 8080)
        btnChat()


    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeScreenActivity::class.java).apply {
            putExtra("DESIRED_FRAGMENT", "profile")
        }

        startActivity(intent)
    }

    private fun btnChat() {
        button_chat.setOnClickListener {
            val massage: String = tvChat.text.toString()
            val response = HttpActivity()
            val nieco = response.send(massage)
        }
    }

}