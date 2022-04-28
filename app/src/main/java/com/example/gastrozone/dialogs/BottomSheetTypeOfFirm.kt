package com.example.gastrozone.dialogs

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.gastrozone.R
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.bottom_sheet_type_of_firm.*
import kotlinx.android.synthetic.main.bottom_sheet_type_of_firm.view.*
import kotlinx.android.synthetic.main.fragment_firstsettings.*
import org.json.JSONObject
import org.json.JSONTokener

class BottomSheetTypeOfFirm(private var mBottomSheetListener: BottomSheetListener) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.bottom_sheet_type_of_firm, container, false)

        var responseData: JSONObject = JSONObject()

        Thread(Runnable {
            val httpClient = HttpActivity()
            val baseUrl = "http://37.9.170.36:8080"
            val typesUrl = "$baseUrl/v1/api/firm/types"

            val data = httpClient.ExecGETRequest(uri = typesUrl)

            responseData = JSONTokener(data).nextValue() as JSONObject
            val types = responseData.getJSONArray("types")
            for(t in 0 until types.length()) {
                val type = types.getJSONObject(t)
                println("${type.get("id")} -> ${type.get("name")}")

                val typeTextView = TextView(this.context)
                typeTextView.id = type.get("id") as Int
                typeTextView.text = type.get("name") as String
                typeTextView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                typeTextView.textSize = 15 * getResources().getDisplayMetrics().scaledDensity;
                typeTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

                firm_type_layout.addView(typeTextView)


            }
            println(v.touchables)

        }).start()

        // To handle clicks
        /*v.tvKaviaren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Kaviareň")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvRestauracia.setOnClickListener {
            mBottomSheetListener.onOptionClick("Reštaurácia")
            dismiss()
        }
        v.tvBar.setOnClickListener {
            mBottomSheetListener.onOptionClick("Bar")
            dismiss()
        }
        v.tvKrcma.setOnClickListener {
            mBottomSheetListener.onOptionClick("Krčma")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvCukraren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Cukráreň")
            dismiss()
        }
        v.tvRychleObcerstvenie.setOnClickListener {
            mBottomSheetListener.onOptionClick("Rýchle občerstvenie")
            dismiss()
        }
        v.tvVinaren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Vináreň")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvCajovna.setOnClickListener {
            mBottomSheetListener.onOptionClick("Čajovňa")
            dismiss()
        }
        */

        return v
    }

    interface BottomSheetListener{
        fun onOptionClick(text:String)
    }

}