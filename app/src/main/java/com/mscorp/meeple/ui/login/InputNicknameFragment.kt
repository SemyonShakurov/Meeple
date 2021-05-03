package com.mscorp.meeple.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mscorp.meeple.R

class InputNicknameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nickname_input, container, false)

        view.findViewById<ImageView>(R.id.imageViewBackFromInputNickname).setOnClickListener {
            activity?.onBackPressed()
        }

        view.findViewById<Button>(R.id.buttonContinue).setOnClickListener {

        }

        return view
    }
}