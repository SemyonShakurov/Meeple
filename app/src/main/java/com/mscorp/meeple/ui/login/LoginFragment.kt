package com.mscorp.meeple.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.databinding.FragmentLoginBinding
import com.mscorp.meeple.ui.main.MenuActivity
import com.mscorp.meeple.ui.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            viewModel.login(binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString())
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            if (it is Event.Loading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.INVISIBLE
            if (it is Event.Success)
            {
                val intent = Intent(context, MenuActivity::class.java)
                intent.putExtra("username", it.value.username)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else if (it is Event.Failure) {
                Toast.makeText(context, "Some problems", Toast.LENGTH_SHORT).show()
            }
        })
    }

}