package com.mscorp.meeple.ui.main.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.mscorp.meeple.databinding.FragmentAddNewGameBinding
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.ui.viewmodel.UserViewModel

class AddNewGameFragment : Fragment() {

    private lateinit var binding: FragmentAddNewGameBinding
    private val viewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewBackFromAddNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_addNewGameFragment_to_myGamesFragment)
        }

        val adapterGames =
            SmallGamesAdapter(
                viewModel.getNotUsersGames(),
                TypeOfGameList.ADD,
                findNavController(),
                viewModel
            )

        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerViewNewGAmes.addItemDecoration(itemDecor)
        binding.recyclerViewNewGAmes.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewNewGAmes.adapter = adapterGames

        viewModel.addGameResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Request.Success -> {
                    viewModel.user.games?.add(it.value.id)
                    adapterGames.setNewData(viewModel.getNotUsersGames())
                    Toast.makeText(context, "Игра добавлена", Toast.LENGTH_SHORT).show()
                    viewModel.addGameResponse = MutableLiveData()
                }
                is Request.Failure -> {
                    Toast.makeText(context, it.errorBody, Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })
    }
}