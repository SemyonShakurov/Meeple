package com.mscorp.meeple.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.TypeOfGameList

class SmallGamesAdapter(private val dataSet: Array<BoardGame>, val type: TypeOfGameList) :
    RecyclerView.Adapter<SmallGamesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.textViewSmallGame)
        var imageViewAddDelete: ImageView = view.findViewById(R.id.imageViewAddDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_small_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = dataSet[position].name

        when (type) {
            TypeOfGameList.SMALL -> holder.imageViewAddDelete.visibility = View.INVISIBLE
            TypeOfGameList.MY -> {
                holder.imageViewAddDelete.visibility = View.VISIBLE
                holder.imageViewAddDelete.setImageResource(R.drawable.ic__delete)
            }
            TypeOfGameList.ADD -> {
                holder.imageViewAddDelete.visibility = View.VISIBLE
                holder.imageViewAddDelete.setImageResource(R.drawable.ic__add)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}