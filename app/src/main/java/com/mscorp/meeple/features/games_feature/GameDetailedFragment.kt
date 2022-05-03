package com.mscorp.meeple.features.games_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mscorp.meeple.databinding.FragmentGameDetailedBinding
import com.mscorp.meeple.model.BoardGame
import com.squareup.picasso.Picasso

internal class GameDetailedFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailedBinding
    lateinit var game: BoardGame

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        game = arguments?.getSerializable("game") as BoardGame
        binding.imageViewBackFromMyGames.setOnClickListener{
            findNavController().navigate(arguments?.getInt("back")!!)
        }

        Picasso.get().load(game.pic).into(binding.roundedImageViewGame)
        binding.textViewGameTitle.text = game.name
        binding.textViewCountOfPlayers.text = game.countPlayer
        binding.textViewTime.text = game.time.toString() + " минут"
        binding.textViewOtherInfo.text = game.description
    }
}