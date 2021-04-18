package com.mscorp.meeple.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mscorp.meeple.api.Request
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

        binding.buttonRegistration.setOnClickListener {
            viewModel.register(
                binding.editTextUsername.text.trim().toString(),
                binding.editTextEmail.text.trim().toString(),
                binding.editTextConfirmPassword.text.trim().toString()
            )
        }


        binding.imageViewBack.setOnClickListener{
            activity?.supportFragmentManager?. popBackStack()
        }

        binding.textViewHaveAccount.setOnClickListener {
            activity?.supportFragmentManager?. popBackStack()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when(it){
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                    binding.progressBarRegister.visibility = View.INVISIBLE
                }
                is Request.Success -> {
                    binding.progressBarRegister.visibility = View.INVISIBLE
                }
                is Request.Loading -> binding.progressBarRegister.visibility = View.VISIBLE
            }
        })
    }
}