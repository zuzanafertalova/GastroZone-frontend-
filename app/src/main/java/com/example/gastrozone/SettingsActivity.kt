package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gastrozone.dialogs.DialogDeleteAccount
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_firstsettings.*
import kotlinx.android.synthetic.main.fragment_set_username.*
import org.json.JSONObject
import org.json.JSONTokener

class SettingsActivity : AppCompatActivity() {

    var isButtonChangeUsernameClicked: Boolean = false
    var isButtonChangeProfilePicClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        Thread(Runnable {
            val baseUrl = "http://37.9.170.36:8080"
            val httpClient = HttpActivity()
            val token = getIntent().getStringExtra("token")
            val data = httpClient.ExecGETRequest(uri = "$baseUrl/v1/api/user")
            val jsonObject = JSONTokener(data).nextValue() as JSONObject
            val is_company = jsonObject.getString("is_company").toBoolean()
            if (!is_company) {
                buttonChangeDescrib.visibility = View.GONE
                buttonChangeAddress.visibility = View.GONE
                buttonChangeTypPodniku.visibility = View.GONE
                buttonChangeMenu.visibility = View.GONE
                buttonChangeOpeningHours.visibility = View.GONE
            }
        }).start()

        fragmentChangePassword.view?.visibility = View.GONE
        fragmentChangeUsername.view?.visibility = View.GONE
        fragmentChangeProfilePic.view?.visibility = View.GONE

        btnOpenChangeFragmentSet()
        buttonDeleteAccountOnClick()
        changeUsername()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeScreenActivity::class.java).apply {
            putExtra("DESIRED_FRAGMENT", "profile")
        }

        startActivity(intent)
    }

    private fun btnOpenChangeFragmentSet() {
        buttonChangePassword.setOnClickListener {
            if (fragmentChangePassword.view?.visibility == View.GONE) {
                fragmentChangePassword.view?.visibility = View.VISIBLE
                //btnChangePasswordSet()
            } else {
                fragmentChangePassword.view?.visibility = View.GONE
            }

        }
        buttonChangeUsername.setOnClickListener(View.OnClickListener {
            if (isButtonChangeUsernameClicked == false) {
                isButtonChangeUsernameClicked = true
                fragmentChangeUsername.view?.visibility = View.VISIBLE
            } else {
                isButtonChangeUsernameClicked = false
                fragmentChangeUsername.view?.visibility = View.GONE
            }
        })
        buttonChangeProfilePic.setOnClickListener(View.OnClickListener {
            if (isButtonChangeProfilePicClicked == false) {
                isButtonChangeProfilePicClicked = true
                fragmentChangeProfilePic.view?.visibility = View.VISIBLE
            } else {
                isButtonChangeProfilePicClicked = false
                fragmentChangeProfilePic.view?.visibility = View.GONE
            }
        })

    }

    private fun buttonDeleteAccountOnClick() {
        buttonDeleteAccount.setOnClickListener {
            val inflater = layoutInflater
            val dialog = DialogDeleteAccount().onCreateDialog(this, inflater)

            dialog.show()
        }
    }

    /*private fun btnChangePasswordSet() {
        btnChangePassword.setOnClickListener(View.OnClickListener {
            if (tvCurrPassword.text.isNotEmpty() && tvNewPassword.text.isNotEmpty() && tvNewPasswordConfirm.text.isNotEmpty()) {
                authAdapter.reauthenticate(
                    tvCurrPassword.text.toString(),
                    EventListener { isTrue, _ ->
                        if (isTrue!!) {
                            if (tvNewPassword.text.toString()
                                    .equals(tvNewPasswordConfirm.text.toString())
                            ) {
                                authAdapter.changePassword(
                                    tvNewPassword.text.toString(),
                                    EventListener { isTrue, _ ->
                                        if (isTrue!!) {
                                            Toast.makeText(
                                                this,
                                                "Heslo ??spe??ne zmenen??.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            fragmentChangePassword.view?.visibility = View.GONE
                                        }
                                    })
                            } else {
                                Toast.makeText(this, "Hesl?? sa nezhoduj??.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(this, "S????asn?? heslo je nespr??vne.", Toast.LENGTH_SHORT)
                                .show()
                        }
                        fragmentChangePassword.view?.visibility = View.GONE
                    })
            }

        })
    }*/

    private fun changeUsername() {
        btnSetUsername2.setOnClickListener(View.OnClickListener {
            if (tvSetUsername2.text.isNotEmpty()) {
                val meno = tvSetUsername2.text.toString()

                Thread(Runnable {
                    val httpClient = HttpActivity()
                    val requestUrl = "http://37.9.170.36:8080/change"
                    val jsonPut = "{\"name\": \"$meno\"}"
                    httpClient.ExecPUTRequest(requestUrl, "", jsonPut)
                }).start()
                runOnUiThread {
                    Toast.makeText(this, "Zmena mena prebehla ??spe??ne!", Toast.LENGTH_SHORT).show()
                    fragmentChangeUsername.view?.visibility = View.GONE
                }

            }
        })
    }


}