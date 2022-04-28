package com.example.gastrozone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.R

class SettingsActivity : AppCompatActivity() {
    var isButtonChangeUsernameClicked : Boolean = false
    var isButtonChangePasswordClicked : Boolean = false
    var isButtonChangeProfilePicClicked : Boolean = false
    var isButtonChangeDescribClicked : Boolean = false
    var isButtonChangeAddressClicked : Boolean = false
    var isButtonChangeTypPodnikuClicked : Boolean = false
    var isButtonChangeMenuClicked : Boolean = false
    var isButtonChangeOpeningHoursClicked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

    }

}