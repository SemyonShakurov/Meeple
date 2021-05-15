package com.mscorp.meeple.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.mscorp.meeple.R
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.ui.viewmodel.RegistrationViewModel

class InputNicknameFragment : Fragment() {

    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

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
            val nickname = view.findViewById<EditText>(R.id.editTextNickname).text.toString()
            registrationViewModel.sendCode("@$nickname")
        }

        registrationViewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
                is Request.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            R.id.mainFragmentContainer,
                            ConfirmCodeFragment.newInstance(it.value.email, "", true)
                        )
                        ?.commit()
                }
            }
        }

        return view
    }
}