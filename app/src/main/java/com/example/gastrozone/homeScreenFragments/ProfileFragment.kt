package com.example.gastrozone.homeScreenFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gastrozone.ChatActivity
import com.example.gastrozone.LoginActivity
import com.example.gastrozone.R
import com.example.gastrozone.SettingsActivity
import com.example.gastrozone.adapters.ViewPagerAdapter
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import org.json.JSONObject
import org.json.JSONTokener

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnOtvbaracieHodiny()
        btnLogOutSetClick()
        setUserToTextFields()
        btnSettingsSetClick()
    }

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    fun btnLogOutSetClick() {
        btnLogOut.setOnClickListener(View.OnClickListener {
            // potrebujem, aby sa odhlásil user
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
    }

    fun btnSettingsSetClick() {
        btnSettings.setOnClickListener {
            runOnUiThread {
                val intent = Intent(activity, SettingsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                activity?.startActivity(intent)
            }

        }
    }
    fun btnOtvbaracieHodiny() {
        btnOtvaracieHodiny.setOnClickListener {
            runOnUiThread {
                val intent = Intent(activity, ChatActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                activity?.startActivity(intent)
            }

        }
    }

    fun setUserToTextFields() {
        Thread(Runnable {
            val baseUrl = "http://37.9.170.36:8080"
            val httpClient = HttpActivity()
            val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")
            val responseData = JSONTokener(data).nextValue() as JSONObject
            val isCompany = responseData.getString("is_company").toBoolean()

            if (!isCompany) {
                tvUsername.text = responseData.getString("name")?.toString()
                tvEmail.text = responseData.getString("email")?.toString()

                //storageAdapter.getProfilePic(DbAdapterUser.userUser.profilePic!!, ivProfile_image)
                runOnUiThread {
                    tvAdresaPodniku.visibility = View.GONE
                    tvPopisPodniku.visibility = View.GONE
                    btnOtvaracieHodiny.visibility = View.GONE
                    tvTypPodniku.visibility = View.GONE
                    btnNapojovylistok.visibility = View.GONE
                    btnAddPost.visibility = View.GONE
                    tvPostsProfileFragment.visibility = View.GONE
                    val titles = ArrayList<String>()
                    titles.add("0")
                    titles.add("0")
                    setUserViewPager(titles)
                }


            } else {
                runOnUiThread {
                    tvUsername.text = responseData.getString("name")?.toString()
                    tvEmail.text = responseData.getString("email")?.toString()
                    tvTypPodniku.text = responseData.getString("type")?.toString()
                    //storageAdapter.getProfilePic(DbAdapterUser.userFirma.profilePic!!, ivProfile_image)
                    val titles = ArrayList<String>()
                    titles.add("0")
                    titles.add("0")
                    titles.add("0")
                    setFirmaViewPager(titles)
                }
            }
        }).start()

    }

    fun setUserViewPager(titles: ArrayList<String>) {
        val viewPagerAdapterProfileFragment: ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfileFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfileFragment.addManagerProfile(ReviewsFragment(), titles[0])
            viewPagerAdapterProfileFragment.addManagerProfile(FollowersProfileFragment(), titles[1])
            viewPagerProfileFragment.adapter = viewPagerAdapterProfileFragment
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }

    fun setFirmaViewPager(titles: ArrayList<String>) {
        val viewPagerAdapterProfileFragment: ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfileFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfileFragment.addManagerProfile(ReviewsFragment(), titles[0])
            viewPagerAdapterProfileFragment.addManagerProfile(PostsFragment(), titles[1])
            viewPagerAdapterProfileFragment.addManagerProfile(FollowersProfileFragment(), titles[2])
            viewPagerProfileFragment.adapter = viewPagerAdapterProfileFragment
            viewPagerProfileFragment.currentItem = 0
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }

}