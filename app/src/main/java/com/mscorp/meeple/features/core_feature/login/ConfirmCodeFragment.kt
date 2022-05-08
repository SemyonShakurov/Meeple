package com.mscorp.meeple.features.core_feature.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.observe
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.features.core_feature.MenuActivity
import com.mscorp.meeple.features.core_feature.view_models.LoginViewModel
import com.mscorp.meeple.features.core_feature.view_models.RegistrationViewModel
import com.mscorp.meeple.model.*

internal class ConfirmCodeFragment : MeepleFragment<LoginViewModel>() {

    private var user: User? = null
    private var userFriends: UserFriends? = null
    private lateinit var registrationViewModel: RegistrationViewModel

    companion object {
        const val EMAIL_KEY = "Email"
        const val BOOLEAN_KEY = "Boolean"
        const val PASS_KEY = "pass"

        fun newInstance(
            email: String,
            pass: String,
            isReset: Boolean = false
        ): ConfirmCodeFragment {
            val fragment = ConfirmCodeFragment()
            val bundle = Bundle()
            bundle.putString(EMAIL_KEY, email)
            bundle.putBoolean(BOOLEAN_KEY, isReset)
            bundle.putString(PASS_KEY, pass)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationViewModel =createViewModel()
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


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationViewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
                is Request.Success -> {
                    if (!arguments?.getBoolean(BOOLEAN_KEY)!!) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        user = it.value
                        viewModel.getFriends(it.value.id)
                    } else {
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                R.id.mainFragmentContainer,
                                ResetPasswordFragment.newInstance(it.value.id)
                            )
                            ?.commit()
                    }
                }
            }
        }

        if (!arguments?.getBoolean(BOOLEAN_KEY)!!) {
            viewModel.friendsResponse.observe(viewLifecycleOwner) {
                if (it is Request.Success) {
                    userFriends = it.value
                    viewModel.getAllGames()
                } else if (it is Request.Failure) {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.gamesResponse.observe(viewLifecycleOwner) {
            if (it is Request.Success) {
                login(it.value)
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                login(listOf())
            }
        }

    }

    fun login(boardGames: List<BoardGame>) {
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("user", user)
        intent.putExtra("friends", userFriends)
        intent.putExtra("games", BoardGames(boardGames))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}