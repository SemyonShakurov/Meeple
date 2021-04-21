package com.mscorp.meeple.ui.main.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentMyFriendsBinding
import com.mscorp.meeple.ui.adapters.FriendsAdapter
import com.mscorp.meeple.ui.viewmodel.UserViewModel


class MyFriendsFragment() : Fragment() {

    private lateinit var binding: FragmentMyFriendsBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FriendsAdapter(viewModel.userFriends.friends, false, viewModel)

        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerViewMyFriends.addItemDecoration(itemDecor)
        binding.recyclerViewMyFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMyFriends.adapter = adapter

        binding.imageViewBackFromMyFriends.setOnClickListener {
            findNavController().navigate(R.id.action_myFriendsFragment_to_navigation_profile)
        }
        binding.imageViewAddNewFriend.setOnClickListener {
            findNavController().navigate(R.id.action_myFriendsFragment_to_addNewFriendsFragment)
        }
    }
}