package com.mscorp.meeple.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.databinding.FragmentRegistrationBinding
import com.mscorp.meeple.ui.viewmodel.RegistrationViewModel


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    val viewModel = RegistrationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.setOnClickListener {
            viewModel.register(
                binding.editTextRegEmail.text.toString(),
                binding.editTextUsername.text.toString(),
                binding.editTextRegPassword.text.toString()
            )
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when(it){
                is Event.Failure -> {
                    Toast.makeText(context, "Problems", Toast.LENGTH_SHORT).show()
                    binding.progressBarReg.visibility = View.INVISIBLE
                }
                is Event.Success -> onLoginSuccess()
                is Event.Loading -> binding.progressBarReg.visibility = View.VISIBLE
            }
        })
    }

    private fun onLoginSuccess(){
        binding.progressBarReg.visibility = View.INVISIBLE
    }

}