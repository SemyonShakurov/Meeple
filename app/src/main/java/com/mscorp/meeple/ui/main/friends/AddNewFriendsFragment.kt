package com.mscorp.meeple.ui.main.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentAddNewFriendsBinding
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.ui.adapters.FriendsAdapter
import com.mscorp.meeple.ui.viewmodel.LoginViewModel
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import java.util.*

class AddNewFriendsFragment : Fragment() {

    private lateinit var binding: FragmentAddNewFriendsBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)
    private lateinit var viewModelLogin: LoginViewModel
    private lateinit var adapter: FriendsAdapter
    private var users: MutableList<User> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelLogin = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.searchViewFriends.setOnClickListener {
            binding.searchViewFriends.onActionViewExpanded()
        }

        binding.searchViewFriends.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val query = binding.searchViewFriends.query.toString().toLowerCase(Locale.ROOT)
                if (query.isBlank())
                    adapter.setNewData(users)
                else {
                    val copy = users.filter {
                        it.name.toLowerCase(Locale.ROOT)
                            .startsWith(query) || it.nickname.toLowerCase(
                            Locale.ROOT
                        )
                            .startsWith("@" + query)
                    }
                    adapter.setNewData(copy as MutableList<User>)
                }
                return false
            }
        })

        adapter = FriendsAdapter(
            mutableListOf(),
            true,
            viewModel,
            viewModelLogin
        )

        viewModel.getAllUsers()

        viewModel.acceptFriendRequestResponse.observe(viewLifecycleOwner) {
            if (it is Request.Success) {
                viewModel.userFriends.declined.remove(it.value)
                viewModel.userFriends.received.remove(it.value)
                viewModel.userFriends.friends.add(it.value)

                adapter.dataSet.remove(it.value)
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "Запрос в друзья одобрен", Toast.LENGTH_SHORT).show()
                viewModel.acceptFriendRequestResponse = MutableLiveData()
            } else if (it is Request.Failure) {
                Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.sendFriendRequestResponse.observe(viewLifecycleOwner) {
            if (it is Request.Loading)
                binding.progressBarNewFriends.visibility = View.VISIBLE
            else {
                binding.progressBarNewFriends.visibility = View.INVISIBLE
                if (it is Request.Success) {
                    viewModel.userFriends.sent.add(it.value)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(context, "Запрос в друзья отправлен", Toast.LENGTH_SHORT).show()
                    viewModel.sendFriendRequestResponse = MutableLiveData()
                } else if (it is Request.Failure) {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getAllUsersResponse.observe(viewLifecycleOwner) {
            if (it is Request.Loading)
                binding.progressBarNewFriends.visibility = View.VISIBLE
            else {
                binding.progressBarNewFriends.visibility = View.INVISIBLE

                if (it is Request.Success) {
                    users = it.value
                    users.remove(viewModel.user)
                    users.removeAll(viewModel.userFriends.friends)
                    users.removeAll(viewModel.userFriends.received)
                    adapter.setNewData(users)
                    adapter.notifyDataSetChanged()
                } else if (it is Request.Failure) {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerViewNewFriends.addItemDecoration(itemDecor)
        binding.recyclerViewNewFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewNewFriends.adapter = adapter

        binding.imageViewBackFromAddNewFriend.setOnClickListener {
            findNavController().navigate(R.id.action_addNewFriendsFragment_to_myFriendsFragment)
        }
    }
}