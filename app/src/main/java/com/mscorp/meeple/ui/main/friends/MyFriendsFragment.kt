package com.mscorp.meeple.ui.main.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentMyFriendsBinding
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.ui.adapters.FriendsAdapter
import com.mscorp.meeple.ui.adapters.FriendsRequestAdapter
import com.mscorp.meeple.ui.viewmodel.LoginViewModel
import com.mscorp.meeple.ui.viewmodel.UserViewModel


class MyFriendsFragment : Fragment() {

    private lateinit var binding: FragmentMyFriendsBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)
    private lateinit var viewModelLogin: LoginViewModel


    companion object {
        lateinit var clickedUser: User
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclers()
        setupOnCLick()

        val bundle = Bundle()
        bundle.putSerializable("user", viewModel.user)
        bundle.putSerializable("friends", viewModel.userFriends)
    }

    private fun setupRecyclers() {
        viewModelLogin = ViewModelProvider(this)[LoginViewModel::class.java]
        val adapter = FriendsAdapter(
            viewModel.userFriends.friends,
            false,
            viewModel,
            viewModelLogin
        )
        val itemDecor = DividerItemDecoration(context, 1)

        binding.recyclerViewMyFriends.addItemDecoration(itemDecor)
        binding.recyclerViewMyFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMyFriends.adapter = adapter

        val adapterRequests = FriendsRequestAdapter(viewModel.userFriends.received, viewModel)
        binding.recyclerViewRequests.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRequests.adapter = adapterRequests

        viewModel.declineFriendRequestResponse.observe(viewLifecycleOwner, {
            if (it is Request.Success) {
                viewModel.userFriends.received.remove(it.value)
                viewModel.userFriends.declined.add(it.value)

                adapterRequests.setNewData(viewModel.userFriends.received)
                adapterRequests.notifyDataSetChanged()

                Toast.makeText(context, "Заявка в друзья отклонена", Toast.LENGTH_SHORT).show()
                viewModel.declineFriendRequestResponse = MutableLiveData()
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.deleteFriendRequestResponse.observe(viewLifecycleOwner, {
            if (it is Request.Success) {
                viewModel.userFriends.friends.remove(it.value)
                viewModel.userFriends.declined.add(it.value)

                adapter.setNewData(viewModel.userFriends.friends)
                adapter.notifyDataSetChanged()

                Toast.makeText(context, "Друг удалён", Toast.LENGTH_SHORT).show()
                viewModel.deleteFriendRequestResponse = MutableLiveData()
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.acceptFriendRequestResponse.observe(viewLifecycleOwner, {
            if (it is Request.Success) {
                viewModel.userFriends.received.remove(it.value)
                viewModel.userFriends.friends.add(it.value)

                adapterRequests.setNewData(viewModel.userFriends.received)
                adapterRequests.notifyDataSetChanged()
                adapter.setNewData(viewModel.userFriends.friends)
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "Запрос в друзья одобрен", Toast.LENGTH_SHORT).show()
                viewModel.acceptFriendRequestResponse = MutableLiveData()
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })


        viewModelLogin.friendsResponse.observe(viewLifecycleOwner, {
            if (it is Request.Success) {
                val bundle = Bundle()
                bundle.putSerializable("user", clickedUser)
                bundle.putSerializable("friends", it.value)
                bundle.putSerializable("back", R.id.action_friendDetailedFragment_to_myFriendsFragment)
                   if (findNavController().currentDestination?.id == R.id.myFriendsFragment)
                    findNavController().navigate(
                        R.id.action_myFriendsFragment_to_friendDetailedFragment,
                        bundle
                    )
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupOnCLick() {
        binding.imageViewBackFromMyFriends.setOnClickListener {
            findNavController().navigate(R.id.action_myFriendsFragment_to_navigation_profile)
        }
        binding.imageViewAddNewFriend.setOnClickListener {
            findNavController().navigate(R.id.action_myFriendsFragment_to_addNewFriendsFragment)
        }
    }

}