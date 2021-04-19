package com.mscorp.meeple.ui.main

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.adapters.SmallFiendsAdapter
import com.mscorp.meeple.adapters.SmallGamesAdapter
import com.mscorp.meeple.databinding.FragmentProfileBinding
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.model.User
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
        } catch (ignored: Exception) {
        }


        setupAdapters()
        setupOnCLickListeners()
    }

    fun setupOnCLickListeners(){

        binding.imageViewGamesInfo.setOnClickListener{
            findNavController().navigate(R.id.myGamesFragment)
        }
    }

    fun setupAdapters(){
        binding.textViewUsername.text = viewModel.user.name
        binding.textViewNickname.text = viewModel.user.nickname

        val adapter = SmallFiendsAdapter(
            arrayOf(
                User(
                    1,
                    "",
                    "",
                    "Чорт Паганый",
                    "https://image.flaticon.com/icons/png/512/168/168726.png",
                    null,
                    null,
                    null
                ),
                User(
                    1,
                    "",
                    "",
                    "Панк Фёдоров",
                    "https://image.flaticon.com/icons/png/512/168/168732.png",
                    null,
                    null,
                    null
                ),
                User(
                    1,
                    "",
                    "",
                    "Инста Самка",
                    "https://image.flaticon.com/icons/png/512/168/168719.png",
                    null,
                    null,
                    null
                )
            )
        )

        fun RecyclerView.addItemDecorationWithoutLastDivider() {

            if (layoutManager !is LinearLayoutManager)
                return

            addItemDecoration(object :
                DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation) {

                override fun getItemOffsets( outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)

                    if (parent.getChildAdapterPosition(view) == state.itemCount - 1)
                        outRect.setEmpty()
                    else
                        super.getItemOffsets(outRect, view, parent, state)
                }
            })
        }


        binding.recyclerFiends.addItemDecorationWithoutLastDivider()
        binding.recyclerFiends.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerFiends.adapter = adapter

        val adapterGames = SmallGamesAdapter(
            arrayOf(
                BoardGame(1, "Монополия", "", 4, 2, "", ""),
                BoardGame(1, "Дженга", "", 4, 2, "", ""),
                BoardGame(1, "UNO", "", 4, 2, "", ""),
            ), TypeOfGameList.SMALL
        )


        binding.recyclerGames.addItemDecorationWithoutLastDivider()
        binding.recyclerGames.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerGames.adapter = adapterGames
    }



}