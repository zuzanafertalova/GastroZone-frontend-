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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.bottom_sheet_filters.*
import org.json.JSONObject
import org.json.JSONTokener

class HomeScreenActivity : AppCompatActivity(), BottomSheetTypeOfFirm.BottomSheetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val token = getIntent().getStringExtra("token")
        val baseUrl = "http://37.9.170.36:8080"
        var type = 5

//ak je to user, tak nastavenie typu podniku zmizne
        Thread(Runnable {
            val httpClient = HttpActivity()
            val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")
            val jsonObject = JSONTokener(data).nextValue() as JSONObject
            val is_company = jsonObject.getString("is_company").toBoolean()
            if (!is_company) {
                tvChangeTypPodniku.visibility = View.GONE
                btnShowOptions.visibility = View.GONE
                tvTypPodnikuChosen.visibility = View.GONE
            }
        }).start()


        /*btnShowOptions.setOnClickListener(View.OnClickListener {
            setContentView(R.layout.bottom_sheet_filters)
            tvRestauraciaFilter.setOnClickListener(View.OnClickListener {
                type = 1
            })
        })*/
        btnSetFirstSettings.setOnClickListener(View.OnClickListener {
            Thread(Runnable {
                val httpClient = HttpActivity()
                val name: String = tvSetUsername.text.toString()
                val jsonPut = "{\"name\": \"$name\"}"
                val data = httpClient.ExecPUTRequest(uri = "$baseUrl/change", token, jsonPut)


            }).start()

        })


        setUsernameFragment()
        setUpOpenBottomSheetTypeOfFirm()
        //btnSetUsernameSetClick()
    }

    fun setUsernameFragment() {

        var username: String = ""

        Thread(Runnable {
            val baseUrl = "http://37.9.170.36:8080"
            val httpClient = HttpActivity()
            val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")
            val jsonObject = JSONTokener(data).nextValue() as JSONObject
            username = jsonObject.getString("name")
            if (!username.isEmpty()) {
                runOnUiThread {
                    fragmentSetUserame.view?.visibility = View.GONE
                    setViewPager()
                }
            }
        }).start()
    }

    fun setUpOpenBottomSheetTypeOfFirm() {
        btnShowOptions.setOnClickListener {
            val bottomSheet = BottomSheetTypeOfFirm(this)
            supportFragmentManager.let { it1 ->
                bottomSheet.show(
                    it1,
                    "BottomSheetDialogTypPodniku"
                )
            }
        }
    }

    override fun onOptionClick(text: String) {
        tvTypPodnikuChosen.text = text
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