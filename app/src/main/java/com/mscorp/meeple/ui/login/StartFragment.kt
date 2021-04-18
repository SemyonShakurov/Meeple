package com.mscorp.meeple.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mscorp.meeple.R
import com.mscorp.meeple.api.Request
import com.mscorp.meeple.databinding.FragmentStartBinding
import com.mscorp.meeple.ui.main.MenuActivity
import com.mscorp.meeple.ui.viewmodel.LoginViewModel

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val viewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            if (it is Request.Loading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.INVISIBLE
            if (it is Request.Success)
            {
                val intent = Intent(context, MenuActivity::class.java)
                intent.putExtra("user", it.value)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })

        binding.NoAccountTextView.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, RegistrationFragment())
                ?.addToBackStack(null)
                ?.commit();
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(binding.UserNameEditText.text.toString(),
                binding.PasswordEditText.text.toString())
        }

    }
}
