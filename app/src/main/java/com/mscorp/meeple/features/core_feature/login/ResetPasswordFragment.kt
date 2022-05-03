package com.mscorp.meeple.features.core_feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.features.core_feature.view_models.RegistrationViewModel
import com.mscorp.meeple.model.Request

internal class ResetPasswordFragment : MeepleFragment<RegistrationViewModel>() {

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
                viewModel.resetPassword(arguments?.getInt(ID_KEY)!!, password)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
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