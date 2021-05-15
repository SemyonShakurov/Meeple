package com.mscorp.meeple.ui.main.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.mscorp.meeple.databinding.FragmentMyGamesBinding
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.ui.viewmodel.UserViewModel

class MyGamesFragment : Fragment() {

    private lateinit var binding: FragmentMyGamesBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterGames = SmallGamesAdapter(
            viewModel.getUsersGames(),
            TypeOfGameList.MY,
            findNavController(),
            viewModel
        )

        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerViewMyGames.addItemDecoration(itemDecor)
        binding.recyclerViewMyGames.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMyGames.adapter = adapterGames


        viewModel.addGameResponse.observe(viewLifecycleOwner){}
        viewModel.deleteGameResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Success -> {
                    viewModel.user.games?.remove(it.value.id)
                    adapterGames.setNewData(viewModel.getUsersGames())
                    Toast.makeText(context, "Игра удалена", Toast.LENGTH_SHORT).show()
                    viewModel.deleteGameResponse = MutableLiveData()
                }
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }

        binding.imageViewAddNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_myGamesFragment_to_addNewGameFragment)
        }

        binding.imageViewBackFromMyGames.setOnClickListener {
            findNavController().navigate(R.id.action_myGamesFragment_to_navigation_profile)
        }
    }
}