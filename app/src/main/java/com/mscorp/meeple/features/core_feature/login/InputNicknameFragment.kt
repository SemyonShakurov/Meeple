package com.mscorp.meeple.features.core_feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.observe
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.features.core_feature.view_models.RegistrationViewModel
import com.mscorp.meeple.model.Request

internal class InputNicknameFragment : MeepleFragment<RegistrationViewModel>() {

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
            viewModel.sendCode("@$nickname")
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
                            ConfirmCodeFragment.newInstance(it.value.email, "", true)
                        )
                        ?.commit()
                }
            }
        }

        return view
    }
}