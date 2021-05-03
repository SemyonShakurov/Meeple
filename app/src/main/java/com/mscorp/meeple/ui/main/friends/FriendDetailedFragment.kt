package com.mscorp.meeple.ui.main.friends

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
import com.mscorp.meeple.databinding.FragmentFriendDetailedBinding
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.ui.adapters.SmallFiendsAdapter
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import com.squareup.picasso.Picasso


class FriendDetailedFragment : Fragment() {

    private lateinit var binding: FragmentFriendDetailedBinding
    private lateinit var user: User
    private lateinit var friends: UserFriends
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupAdapters()
        setupOnCLickListeners()
    }

    private fun setupViews() {
        user = arguments?.getSerializable("user") as User
        friends = arguments?.getSerializable("friends") as UserFriends

        var name = user.name
        name = name.substring(0, name.indexOf(' ')) + "\n" + name.substring(
            name.indexOf(' '),
            name.length
        )
        binding.textViewUsernameUser.text = name
        binding.textViewNicknameUser.text = user.nickname
        Picasso.get().load(user.photoUrl).into(binding.roundedImageViewAvatarProfileUser)
    }

    private fun setupAdapters() {
        if (friends.friends.isEmpty())
            binding.textViewFriendsNotFound.visibility = View.VISIBLE
        else
            binding.textViewFriendsNotFound.visibility = View.INVISIBLE

        binding.textViewGamesNotFoundUser.visibility = View.VISIBLE

        val adapterFriends = SmallFiendsAdapter(friends.friends.take(3))
        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerFiends.addItemDecoration(itemDecor)
        binding.recyclerFiends.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerFiends.adapter = adapterFriends

        //Игры
        if (viewModel.user.games.isNullOrEmpty())
            binding.textViewGamesNotFoundUser.visibility = View.VISIBLE
        else
            binding.textViewGamesNotFoundUser.visibility = View.INVISIBLE

        val adapterGames = SmallGamesAdapter(
            arrayOf(), TypeOfGameList.SMALL
        )
        binding.recyclerGamesUser.addItemDecoration(itemDecor)
        binding.recyclerGamesUser.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerGamesUser.adapter = adapterGames
    }


    private fun setupOnCLickListeners() {

        binding.imageViewBackFromAddNewFriend.setOnClickListener{
            findNavController().navigate(R.id.action_friendDetailedFragment_to_addNewFriendsFragment)
        }
    }
}