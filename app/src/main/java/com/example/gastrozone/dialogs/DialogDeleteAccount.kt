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
import com.example.gastrozone.LoginActivity
import com.example.gastrozone.R

class DialogDeleteAccount : DialogFragment(){


    fun onCreateDialog(activity: Activity, inflater: LayoutInflater): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Naozaj chcete odstrániť účet?")
        val dialogPassword = inflater.inflate(R.layout.dialog_password, null)
        val tvPassword = dialogPassword.findViewById<EditText>(R.id.editText)
        builder.setView(dialogPassword)
        builder.setPositiveButton("Potvrdiť", DialogInterface.OnClickListener{ dialog, id ->
            val password = tvPassword.text.toString()
            if (password.isNotEmpty()){
                authAdapterUser.reauthenticate(password, EventListener{staloSa,_->
                    if (staloSa==true){
                        algoliaSearchAdapter.deleteObject(authAdapterUser.currentUser?.uid).toString()
                        dbAdapterUser.deleteUserFromDatabase(authAdapterUser.currentUser!!, EventListener{spraviloSA,_->
                            if (spraviloSA!!){
                                startLoginActivity()
                            }
                        })
                    }
                    else{
                        Toast.makeText(activity,"Heslo je nesprávne", Toast.LENGTH_SHORT).show()
                    }
                })
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

    fun startLoginActivity(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}