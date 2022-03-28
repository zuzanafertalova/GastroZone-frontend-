package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register_firm.*

class RegisterFirmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_firm)

        button_register_firm.setOnClickListener(View.OnClickListener {

            val email: String = input_register_email_firm.text.toString()
            val password: String = input_register_pass_firm.text.toString()
            val confrimpassword: String = input_register_passConfirm_firm.text.toString()
            val ico: String = input_register_ico.text.toString()


            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()) {
                Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show()

            }

            else {

                if (password.equals(confrimpassword)) {

                            Toast.makeText(this, "Môžete sa prihlásiť", Toast.LENGTH_SHORT).show()
                            intent = Intent(this, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                else { Toast.makeText(this, "Your passwords does not match", Toast.LENGTH_SHORT).show() }

            }
        })

        button_login_firm.setOnClickListener(View.OnClickListener { ///sa vratim na login
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}