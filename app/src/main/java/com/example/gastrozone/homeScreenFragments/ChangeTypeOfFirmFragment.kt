package com.example.gastrozone.homeScreenFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gastrozone.R
import com.example.gastrozone.dialogs.BottomSheetTypeOfFirm
import kotlinx.android.synthetic.main.fragment_firstsettings.*

class ChangeTypeOfFirmFragment : Fragment(), BottomSheetTypeOfFirm.BottomSheetListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_change_type_of_firm, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onOptionClick(text: String) {
        Log.i("Zvolený typ podniku: ", text)
        tvTypPodnikuChosen.text = text
    }
}