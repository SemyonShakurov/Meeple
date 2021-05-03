package com.mscorp.meeple.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mscorp.meeple.R
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.ui.viewmodel.RegistrationViewModel
import java.lang.NumberFormatException

class ConfirmCodeFragment : Fragment() {

    private lateinit var registrationViewModel: RegistrationViewModel

    companion object {
        const val EMAIL_KEY = "Email"

        fun newInstance(email: String): ConfirmCodeFragment {
            val fragment = ConfirmCodeFragment()
            val bundle = Bundle()
            bundle.putString(EMAIL_KEY, email)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_code, container, false)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            try {
                val code = view.findViewById<EditText>(R.id.edit_text_code).text.toString().toInt()
                registrationViewModel.confirmEmail(arguments?.getString(EMAIL_KEY)!!, code)
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Incorrect code", Toast.LENGTH_SHORT).show()
            }
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
                        ?.replace(R.id.mainFragmentContainer, StartFragment())
                        ?.commit()
                }
            }
        }
        return view
    }
}