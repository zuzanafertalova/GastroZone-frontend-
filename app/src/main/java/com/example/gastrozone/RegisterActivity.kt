package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.activity_register)

        button_login_redirect.setOnClickListener(View.OnClickListener {
            finish()
        })

        button_register.setOnClickListener(View.OnClickListener {
            val email: String = input_register_email.text.toString()
            val password: String = input_register_pass.text.toString()
            val confrimpassword: String = input_register_passConfirm.text.toString()

            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()) {
                Toast.makeText(this, "Vyplňte všetky polia", Toast.LENGTH_SHORT).show()
            } else {
                if (password.equals(confrimpassword)) {
                    val url = "http://37.9.170.36:8080/register"
                    val jsonPost =
                        "{\"password\": \"$password\", \"email\": \"$email\", \"username\": \"$email\"}"
                    val response = HttpActivity()
                    val token = response.register(url, jsonPost)
                    System.out.println(token)

                    Toast.makeText(this, token, Toast.LENGTH_SHORT).show()

                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Heslá sa nezhodujú", Toast.LENGTH_SHORT).show()
                }

            }
        }

        )

        button_register_firm1.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterFirmActivity::class.java)
            startActivity(intent)
        })

    }
}