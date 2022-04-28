package com.example.gastrozone.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.gastrozone.LoginActivity
import com.example.gastrozone.R
import com.example.gastrozone.http.HttpActivity

class DialogDeleteAccount : DialogFragment(){

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    fun onCreateDialog(activity: Activity, inflater: LayoutInflater): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Naozaj chcete odstrániť účet?")
        val dialogPassword = inflater.inflate(R.layout.dialog_password, null)
        val tvPassword = dialogPassword.findViewById<EditText>(R.id.editText)
        builder.setView(dialogPassword)
        builder.setPositiveButton("Potvrdiť", DialogInterface.OnClickListener{ dialog, id ->
            val password = tvPassword.text.toString()
            if (password.isNotEmpty()){
                Thread(Runnable {
                    val httpClient = HttpActivity()
                    val requestUrl = "http://37.9.170.36:8080/v1/api/user"
                    val payloadData = """{"password": "$password"}"""

                    var response: String = httpClient.ExecDeleteRequest(requestUrl, payloadData)
                    println(response)

                }).start()

                Toast.makeText(activity,"Účet bol zmazany!", Toast.LENGTH_SHORT).show()
                activity?.let {
                    val intent = Intent(it, LoginActivity::class.java)
                    it.startActivity(intent)
                }

            }
            else{
                Toast.makeText(activity,"Zadajte Heslo", Toast.LENGTH_SHORT).show()
            }


        })
            .setNegativeButton("Zrušiť",
                DialogInterface.OnClickListener{ dialog, id ->
                })
        return builder.create()
    }
}