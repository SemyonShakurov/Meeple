package com.mscorp.meeple.features.friend_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.databinding.FragmentFriendDetailedBinding
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.ui.adapters.SmallFiendsAdapter
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.squareup.picasso.Picasso


internal class FriendDetailedFragment : MeepleFragment<UserViewModel>() {

    private lateinit var binding: FragmentFriendDetailedBinding
    private lateinit var user: User
    private lateinit var friends: UserFriends

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val adapterFriends = SmallFiendsAdapter(friends.friends)
        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerFiends.addItemDecoration(itemDecor)
        binding.recyclerFiends.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerFiends.adapter = adapterFriends

        //Игры
        if (user.games.isNullOrEmpty())
            binding.textViewGamesNotFoundUser.visibility = View.VISIBLE
        else
            binding.textViewGamesNotFoundUser.visibility = View.INVISIBLE

        val adapterGames =
            SmallGamesAdapter(viewModel.games.filter { user.games!!.contains(it.id) },
                TypeOfGameList.SMALL,
                findNavController(),
                viewModel)

        binding.recyclerGamesUser.addItemDecoration(itemDecor)
        binding.recyclerGamesUser.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerGamesUser.adapter = adapterGames
    }

    private fun setupOnCLickListeners() {

        binding.imageViewBackFromAddNewFriend.setOnClickListener {
            findNavController().navigate(arguments?.getInt("back")!!)
        }
    }
}