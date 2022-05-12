package com.example.gastrozone.homeScreenFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gastrozone.ProfileFromSearchActivity
import com.example.gastrozone.adapters.SearchResultAdapter
import com.example.gastrozone.R
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.fragment_change_profile_pic.*
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

class SearchFragment : Fragment(){

    internal var adapter: SearchResultAdapter? = null

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setEditText()
        setOnClickSearch()
    }

    private fun setUpRecyclerView() {

        searchingRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchResultAdapter(object : SearchResultAdapter.OnResultsClick {
            override fun setOnProfileClickListener(objectID: String?) {
                editText_search.clearFocus()
                val intent = Intent(context, ProfileFromSearchActivity::class.java)
                intent.putExtra("objectID", objectID)
                startActivity(intent)
            }
        })
        searchingRecyclerView.adapter = adapter
    }

    fun setEditText(){
        editText_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text = p0.toString()
                buttonConfirmSearch.setOnClickListener{
                    search(text)
              }
            }
        })
    }

    fun search(content: String) {

        Thread(Runnable {
            val baseUrl = "http://37.9.170.36:8080"
            val httpClient = HttpActivity()
            val requestUrl = "$baseUrl/filters_companies/$content"
            val data = httpClient.ExecGETRequest(uri = requestUrl)
            println(data)
            val responseData = JSONTokener(data).nextValue() as JSONObject
            val resultArray = responseData.getJSONArray("list_of_companies")

            setValuesToList(resultArray)

        }).start()
    }

    @Throws(JSONException::class)
    fun setValuesToList(data: JSONArray) {
        val usernames = ArrayList<String>()
        val objectIDs = ArrayList<String>()
        for (i in 0 until data.length()) {
            val jsonObject = data.getJSONObject(i)
            val username = jsonObject.getString("company_name")
            val objectID = jsonObject.getString("type")
            usernames.add(username)
            objectIDs.add(objectID)
        }
        runOnUiThread {
            this.adapter?.setNewData(usernames,objectIDs)
        }
    }

    private fun setOnClickSearch(){
        editText_search.setOnFocusChangeListener(OnFocusChangeListener { _ , hasFocus ->
            if(hasFocus){
                editText_search.background = resources.getDrawable(R.drawable.search_rectangle_green)
            }
            else {
                editText_search.background = resources.getDrawable(R.drawable.search_rectangle_white)
            }
        })
    }

}