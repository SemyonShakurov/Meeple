package com.mscorp.meeple.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentStartBinding
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.SecurePreferences
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.ui.main.MenuActivity
import com.mscorp.meeple.ui.viewmodel.LoginViewModel


class StartFragment : Fragment() {

    private var user: User? = null
    private lateinit var binding: FragmentStartBinding
    private val viewModel = LoginViewModel()
    private var havAcc: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val preferences =
            SecurePreferences(context, "my-preferences", "SometopSecretKey1235", true)
        //preferences.removeValue("userId")
        //preferences.removeValue("pass")
        binding = FragmentStartBinding.inflate(inflater, container, false)
        val login: String? = preferences.getString("userId")
        return if (login == null) {
            binding.root
        } else {
            havAcc = true
            val pass = preferences.getString("pass")
            viewModel.login(login, pass)
            inflater.inflate(R.layout.fragment_loading, container, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var progressBar: ProgressBar
        if (havAcc)
            progressBar = view.findViewById(R.id.progressBarLoggin)
        else
            progressBar = binding.progressBar

        viewModel.friendsResponse.observe(viewLifecycleOwner, {
            if (it is Request.Loading)
                progressBar.visibility = View.VISIBLE
            else {
                progressBar.visibility = View.INVISIBLE
                if (it is Request.Success) {
                    if (!havAcc) {
                        val preferences =
                            SecurePreferences(
                                context,
                                "my-preferences",
                                "SometopSecretKey1235",
                                true
                            )
                        preferences.put("userId", "@" + binding.UserNameEditText.text.toString())
                        preferences.put("pass", binding.PasswordEditText.text.toString())
                        val pass = preferences.getString("pass")
                    }

                    login(it.value)
                } else if (it is Request.Failure) {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.loginResponse.observe(viewLifecycleOwner, {

            if (it is Request.Loading)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.INVISIBLE
            if (it is Request.Success) {
                user = it.value
                viewModel.getFriends(it.value.id)
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })

        if (havAcc)
            return

        binding.NoAccountTextView.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, RegistrationFragment(), "")
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(
                "@" + binding.UserNameEditText.text.toString(),
                binding.PasswordEditText.text.toString()
            )
        }


    }

    fun login(userFriends: UserFriends) {
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("user", user)
        intent.putExtra("friends", userFriends)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
