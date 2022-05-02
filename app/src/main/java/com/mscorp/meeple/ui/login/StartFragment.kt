package com.mscorp.meeple.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.airbnb.lottie.LottieAnimationView
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentStartBinding
import com.mscorp.meeple.model.*
import com.mscorp.meeple.ui.main.MenuActivity
import com.mscorp.meeple.ui.viewmodel.LoginViewModel

internal class StartFragment : Fragment() {
    private var user: User? = null
    private var userFriends: UserFriends? = null
    private lateinit var binding: FragmentStartBinding
    private val viewModel = LoginViewModel()
    private var havAcc: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val preferences =
            SecurePreferences(context, "my-preferences", "SometopSecretKey1235", true)
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

        setupObservers(view)

       val y = activity?.findViewById<ProgressBar>(R.id.progressBarLoggin)
        y?.visibility = View.INVISIBLE
        val x = activity?.findViewById<LottieAnimationView>(R.id.anim)
        x?.playAnimation()

        if (havAcc)
            return

        setOnClicks()
    }

    private fun setupObservers(view: View) {
        val progressBar = if (havAcc)
            view.findViewById(R.id.progressBarLoggin)
        else
            binding.progressBar

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            if (it is Request.Loading)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.INVISIBLE
            if (it is Request.Success) {
                user = it.value
                if (!user!!.enabled)
                    Toast.makeText(
                        context,
                        "Регастрация не завершена, повторите попытку",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    viewModel.getFriends(it.value.id)
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.friendsResponse.observe(viewLifecycleOwner) {
            if (it is Request.Success) {
                userFriends = it.value
                viewModel.getAllGames()
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.gamesResponse.observe(viewLifecycleOwner) {
            if (it is Request.Loading)
                progressBar.visibility = View.VISIBLE
            else {
                progressBar.visibility = View.INVISIBLE
                if (it is Request.Success) {
                    saveToPref()
                    login(it.value)
                } else if (it is Request.Failure) {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                    login(listOf())
                }
            }
        }

    }

    private fun setOnClicks() {
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

        binding.ForgotPassTextView.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, InputNicknameFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    fun saveToPref()
    {
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
        }
    }

    fun login(boardGames: List<BoardGame>) {
        val x = activity?.findViewById<LottieAnimationView>(R.id.anim)
        x?.progress = 0.99f
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("user", user)
        intent.putExtra("friends", userFriends)
        intent.putExtra("games", BoardGames(boardGames))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}