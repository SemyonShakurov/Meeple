package com.mscorp.meeple.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel

internal class SmallGamesAdapter(
    private var dataSet: List<BoardGame>,
    private val type: TypeOfGameList,
    private val navController: NavController,
    private val viewModel: UserViewModel
) :
    RecyclerView.Adapter<SmallGamesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewSmallGame)
        val imageViewAddDelete: ImageView = view.findViewById(R.id.imageViewAddDelete)
        val layout: ConstraintLayout = view.findViewById(R.id.constrainedGame)
    }

    fun setNewData(data: List<BoardGame>){
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = dataSet[position].name

        holder.layout.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("game", dataSet[position])
            when (type) {
                TypeOfGameList.MY -> {
                    bundle.putInt("back", R.id.action_gameDetailedFragment_to_myGamesFragment)
                    navController.navigate(
                        R.id.action_myGamesFragment_to_gameDetailedFragment,
                        bundle
                    )
                }
                TypeOfGameList.ADD -> {
                    bundle.putInt("back", R.id.action_gameDetailedFragment_to_addNewGameFragment)
                    navController.navigate(
                        R.id.action_addNewGameFragment_to_gameDetailedFragment,
                        bundle)
                }
                else -> {}
            }
        }

        when (type) {
            TypeOfGameList.SMALL -> holder.imageViewAddDelete.visibility = View.INVISIBLE
            TypeOfGameList.MY -> {
                holder.imageViewAddDelete.visibility = View.VISIBLE
                holder.imageViewAddDelete.setImageResource(R.drawable.ic__delete)
                holder.imageViewAddDelete.setOnClickListener{
                    viewModel.deleteGameRequest(viewModel.user.id, dataSet[position].id)
                }
            }
            TypeOfGameList.ADD -> {
                holder.imageViewAddDelete.visibility = View.VISIBLE
                holder.imageViewAddDelete.setImageResource(R.drawable.ic__add)
                holder.imageViewAddDelete.setOnClickListener{
                    viewModel.addGameRequest(viewModel.user.id, dataSet[position].id)
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size

}