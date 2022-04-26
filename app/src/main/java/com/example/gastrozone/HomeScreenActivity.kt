package com.example.gastrozone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_firstsettings.*
import com.example.gastrozone.homeScreenFragments.HomeScreenFragment
import com.example.gastrozone.homeScreenFragments.ProfileFragment
import com.example.gastrozone.homeScreenFragments.SearchFragment
import com.example.gastrozone.adapters.ViewPagerAdapter
import com.example.gastrozone.dialogs.BottomSheetTypeOfFirm
import com.example.gastrozone.R
import com.example.gastrozone.http.HttpActivity
import org.json.JSONObject
import org.json.JSONTokener

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val token = getIntent().getStringExtra("token")
        val baseUrl = "http://37.9.170.36:8080"

        setViewPager()

        /*fetch_data_btn.setOnClickListener {
            Thread(Runnable {
                val httpClient = HttpActivity()
                val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")

                println(data)
                val jsonObject = JSONTokener(data).nextValue() as JSONObject
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val type = jsonObject.getString("type")
                val is_company = jsonObject.getString("is_company").toBoolean()

                runOnUiThread {
                    Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
                }
            }).start()*/
        }


        fun setViewPager() {
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
            viewPagerAdapter.addManagerProfile(SearchFragment(), "")
            viewPagerAdapter.addManagerProfile(HomeScreenFragment(), "")
            viewPagerAdapter.addManagerProfile(ProfileFragment(), "")
            viewPager.adapter = viewPagerAdapter
            viewPager.currentItem = 1
            tabs.setupWithViewPager(viewPager)
            tabs.getTabAt(0)?.setIcon(R.drawable.ic_find_24dp)
            tabs.getTabAt(1)?.setIcon(R.drawable.ic_home)
            tabs.getTabAt(2)?.setIcon(R.drawable.ic_person_24dp)

    }
}