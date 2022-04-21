package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        button_login.setOnClickListener(View.OnClickListener {

            val email: String = input_login_email.text.toString()
            val password : String = input_login_pass.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Vyplňte všetky polia", Toast.LENGTH_SHORT).show()
            }
            else{
                startHomeScreenActivity()
                /*authAdapter.login(email,password, this, EventListener{user,_ ->
                    user?.let {
                        setUserToLocal(it)
                    }
                })*/
            }

        })

        button_create_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })



    }

    /*fun setUserToLocal(user:FirebaseUser){
        dbAdapterUser.setFirebaseUserToLocalUser(user,object : DbInterface{
            override fun onSuccess() {
                startHomeScreenActivity()
            }
            override fun onFailure() {

            }
        })
    }*/

    fun startHomeScreenActivity(){
        intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}