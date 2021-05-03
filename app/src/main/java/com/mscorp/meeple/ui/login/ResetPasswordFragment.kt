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

class ResetPasswordFragment : Fragment() {

    private lateinit var registrationViewModel: RegistrationViewModel

    companion object {
        const val ID_KEY = "Id"

        fun newInstance(id: Int): ResetPasswordFragment {
            val fragment = ResetPasswordFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
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
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        view.findViewById<Button>(R.id.buttonChangePassword).setOnClickListener {
            val password = view.findViewById<EditText>(R.id.editTextNewPassword).text.toString()
            val repeat = view.findViewById<EditText>(R.id.editTextRepeatPassword).text.toString()
            if (password != repeat)
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            else
                registrationViewModel.resetPassword(arguments?.getInt(ID_KEY)!!, password)
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
                            StartFragment()
                        )
                        ?.commit()
                }
            }
        }

        return view
    }
}