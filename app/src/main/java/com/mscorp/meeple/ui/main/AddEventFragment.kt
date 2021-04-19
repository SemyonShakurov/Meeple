package com.mscorp.meeple.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.mscorp.meeple.R

class AddEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_event, container, false)

        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 2
        numberPicker.maxValue = 30
        numberPicker.wrapSelectorWheel = false

        return view
    }
}