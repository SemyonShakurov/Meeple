package com.mscorp.meeple.ui.main

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentProfileBinding
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.ui.adapters.SmallFiendsAdapter
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.mscorp.meeple.ui.viewmodel.UserViewModel


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            viewModel.user = requireArguments().get("user") as User
            viewModel.userFriends = requireArguments().get("friends") as UserFriends
        } catch (ignored: Exception) {
        }

        setupAdapters()
        setupOnCLickListeners()
    }

    fun setupOnCLickListeners() {

        binding.imageViewGamesInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_myGamesFragment)
        }

        binding.imageViewFriendsInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_myFriendsFragment)
        }
    }

    fun setupAdapters() {
        var name = viewModel.user.name
        name = name.substring(0, name.indexOf(' ')) + "\n" + name.substring(
            name.indexOf(' '),
            name.length
        )
        binding.textViewUsername.text = name
        binding.textViewNickname.text = viewModel.user.nickname

        if (viewModel.userFriends.friends.isEmpty())
            binding.textViewFriendsNotFound.visibility = View.VISIBLE
        else
            binding.textViewFriendsNotFound.visibility = View.INVISIBLE

        binding.textViewGamesNotFound.visibility = View.VISIBLE

        val adapter = SmallFiendsAdapter(viewModel.userFriends.friends.take(3))

        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerFiends.addItemDecoration(itemDecor)
        binding.recyclerFiends.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerFiends.adapter = adapter

        if (viewModel.user.games.isNullOrEmpty())
            binding.textViewGamesNotFound.visibility = View.VISIBLE
        else
            binding.textViewGamesNotFound.visibility = View.INVISIBLE

        val adapterGames = SmallGamesAdapter(
            arrayOf(), TypeOfGameList.SMALL
        )

        binding.recyclerGames.addItemDecoration(itemDecor)
        binding.recyclerGames.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerGames.adapter = adapterGames
    }
}