package com.mscorp.meeple.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentLoginBinding
import com.mscorp.meeple.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStartSingIn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, LoginFragment(), "login")
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.buttonStartRegistration.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, RegistrationFragment(), "registration")
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}
