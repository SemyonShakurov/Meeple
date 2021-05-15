package com.mscorp.meeple.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.observe
import com.mscorp.meeple.R
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.databinding.FragmentRegistrationBinding
import com.mscorp.meeple.ui.viewmodel.RegistrationViewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel = RegistrationViewModel()

    internal fun EditText.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegistration.setOnClickListener {
            if (!binding.editTextEmail.isEmailValid())
                Toast.makeText(context, "Неверный формат email!", Toast.LENGTH_SHORT).show()
            else if (binding.editTextConfirmPassword.text.trim() != binding.editTextPassword.text.trim())
                Toast.makeText(context, "Пароли должны совпадать!", Toast.LENGTH_SHORT).show()
            else {
                viewModel.register(
                    binding.editTextName.text.trim().toString(),
                    "@" + binding.editTextUsername.text.trim().toString(),
                    binding.editTextEmail.text.trim().toString(),
                    binding.editTextConfirmPassword.text.trim().toString()
                )
            }
        }

        binding.imageViewBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.textViewHaveAccount.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                    binding.progressBarRegister.visibility = View.INVISIBLE
                }
                is Request.Success -> {
                    binding.progressBarRegister.visibility = View.INVISIBLE

                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            R.id.mainFragmentContainer,
                            ConfirmCodeFragment.newInstance(
                                binding.editTextEmail.text.trim().toString(),
                                binding.editTextPassword.text.trim().toString()
                            )
                        )
                        ?.addToBackStack(null)
                        ?.commit()
                }
                is Request.Loading -> binding.progressBarRegister.visibility = View.VISIBLE
            }
        }
    }
}