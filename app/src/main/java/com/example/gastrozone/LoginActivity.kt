package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.activity_login)


        button_login.setOnClickListener(View.OnClickListener {

            val email: String = input_login_email.text.toString()
            val password: String = input_login_pass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vyplňte všetky polia", Toast.LENGTH_SHORT).show()
            } else {
                val url = "http://37.9.170.36:8080/login"
                val jsonPost = "$email:$password"
                println(jsonPost)
                val encodedString: String =
                    Base64.getEncoder().encodeToString(jsonPost.toByteArray())
                val response = HttpActivity()

                val auth = "Basic $encodedString"
                println(auth)
                val token = response.login(url, auth)
                val url_filter = "http://37.9.170.36:8080/companies/filter-by/type/2"
                val filter = response.filter(url_filter)
                Toast.makeText(this, token, Toast.LENGTH_SHORT).show()

                startHomeScreenActivity()
            }

        })

        button_create_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })


    }


    private fun startHomeScreenActivity() {
        intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}