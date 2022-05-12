package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_from_search.*

class ProfileFromSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_from_search)

        startChat()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeScreenActivity::class.java).apply {
            //putExtra("DESIRED_FRAGMENT", "profile")
        }

        startActivity(intent)
    }

    private fun startChat(){
        btnStartChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                //putExtra("DESIRED_FRAGMENT", "profile")
            }
            startActivity(intent)
        }
    }
}