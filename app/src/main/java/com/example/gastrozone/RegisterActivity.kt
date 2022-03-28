package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(){

    //val authAdapter = AuthAdapter()
    //val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_login_redirect.setOnClickListener(View.OnClickListener {
            finish()
        })

        button_register.setOnClickListener(View.OnClickListener {
            val email : String = input_register_email.text.toString()
            val password : String = input_register_pass.text.toString()
            val confrimpassword: String = input_register_passConfirm.text.toString()

            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()){
                Toast.makeText(this,"Vyplňte všetky polia", Toast.LENGTH_SHORT).show()
            }

            /*else {
                if (password.equals(confrimpassword)){
                    authAdapter.signup(email,password,this, EventListener{user , _->
                        user?.let {
                            Toast.makeText(this, "Úspešná registrácia, môžete sa prihlásiť", Toast.LENGTH_SHORT).show()
                            dbAdapterUser.createUserUserInDatabase(user)
                            finish()
                        }
                    })
                }
                else{
                    Toast.makeText(this,"Heslá sa nezhodujú", Toast.LENGTH_SHORT).show()
                }

            }*/
        }

        )

        button_register_firm1.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterFirmActivity::class.java)
            startActivity(intent)
        })


    }
}