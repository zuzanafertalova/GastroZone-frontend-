package com.example.gastrozone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        /*if(dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            tvChangeTypPodniku2.visibility = View.GONE
            btnShowOptions2.visibility = View.GONE
            tvTypPodnikuChosen2.visibility = View.GONE
        }*/

        //setUsernameFragment()
        //setUpOpenBottomSheetTypPodniku()
        //btnSetUsernameSetClick()

    }
}