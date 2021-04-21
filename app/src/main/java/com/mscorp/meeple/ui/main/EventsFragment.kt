package com.mscorp.meeple.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentEventsBinding
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater)
        binding.imageAddEvent.setOnClickListener {

        }
        return binding.root
    }

}