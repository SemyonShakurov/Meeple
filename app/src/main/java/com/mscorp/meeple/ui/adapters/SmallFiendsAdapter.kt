package com.mscorp.meeple.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.User
import com.squareup.picasso.Picasso

internal class SmallFiendsAdapter(private val dataSet: List<User>)
    : RecyclerView.Adapter<SmallFiendsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewAvatar: RoundedImageView = view.findViewById(R.id.roundedImageViewSmallAvatar)
        var textViewName: TextView = view.findViewById(R.id.textViewSmallName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_small_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(dataSet[position].photoUrl).into(holder.imageViewAvatar)
        holder.textViewName.text = dataSet[position].name
    }

    override fun getItemCount() = dataSet.size

}