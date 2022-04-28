package com.example.gastrozone.homeScreenFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gastrozone.LoginActivity
import com.example.gastrozone.R
import com.example.gastrozone.SettingsActivity
import com.example.gastrozone.adapters.ViewPagerAdapter
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.fragment_firstsettings.*
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

    fun setUserToTextFields() {
        Thread(Runnable {
            val baseUrl = "http://37.9.170.36:8080"
            val httpClient = HttpActivity()
            val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")
            val jsonObject = JSONTokener(data).nextValue() as JSONObject
            val is_company = jsonObject.getString("is_company").toBoolean()
            if (!is_company) {
                //tvUsername.text = DbAdapterUser.userUser.username
                //tvEmail.text = DbAdapterUser.userUser.email
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
                    //titles.add(DbAdapterUser.userUser.reviews.toString())
                    titles.add("0")
                    //titles.add(DbAdapterUser.userUser.following.toString())
                    setUserViewPager(titles)
                }


            } else {
                runOnUiThread {
                    //tvUsername.text = DbAdapterUser.userFirma.username
                    //tvEmail.text = DbAdapterUser.userFirma.email
                    //tvFollowing_FollowersProfileFragment.text = "Sledovatelia"
                    //storageAdapter.getProfilePic(DbAdapterUser.userFirma.profilePic!!, ivProfile_image)
                    //tvTypPodniku.text = DbAdapterUser.userFirma.typPodniku
                    val titles = ArrayList<String>()
                    titles.add("0")
                    //titles.add(DbAdapterUser.userFirma.reviews.toString())
                    titles.add("0")
                    //titles.add(DbAdapterUser.userFirma.posts.size.toString())
                    titles.add("0")
                    //titles.add(DbAdapterUser.userFirma.followers.toString())
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