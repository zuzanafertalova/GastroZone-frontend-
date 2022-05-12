package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UserChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_chat)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeScreenActivity::class.java).apply {
            //putExtra("DESIRED_FRAGMENT", "profile")
        }

        startActivity(intent)
    }
}