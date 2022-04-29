package com.example.gastrozone.homeScreenFragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_change_profile_pic.*
import com.example.gastrozone.R
import com.example.gastrozone.http.HttpActivity
import java.io.File

class ChangeProfilePicFragment : Fragment() {


    private val PICK_IMAGE_REQUEST = 1
    private lateinit var mImageUri: Uri

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_change_profile_pic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBtnLoadProfilePicClick()
        onBtnSaveProfilePicClick()
    }

    fun onBtnSaveProfilePicClick(){
        btnSaveProfilePic2.setOnClickListener{
            println(mImageUri)
            Thread(Runnable {
                val auxFile: File = File(mImageUri.toString())
                val response = HttpActivity()
                val token = response.ExecPOSTImage("http://37.9.170.36:8080/upload", auxFile, auxFile.name)
            }).start()
        }
    }

    fun onBtnLoadProfilePicClick(){
        btnLoadProfilePic2.setOnClickListener{
            openPicChooser()
            ivChangeProfilePic.visibility = View.VISIBLE
        }
    }


    fun openPicChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            ivChangeProfilePic.setImageURI(mImageUri)
        }
    }
}