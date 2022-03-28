package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(){

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnLoginToAccount.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnSignup.setOnClickListener(View.OnClickListener {
            val email : String = tvSignupUsername.text.toString()
            val password : String = pswdSignupPswrd.text.toString()
            val confrimpassword: String = pswdSignupConfirmPswrd.text.toString()

            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()){
                Toast.makeText(this,"Vyplňte všetky polia", Toast.LENGTH_SHORT).show()
            }

            else {
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

            }
        }

        )

        tvSignupFiremnyUcet.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SignupFirmaActivity::class.java)
            startActivity(intent)
        })


    }
}