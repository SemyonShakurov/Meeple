package com.mscorp.meeple.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.User
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import com.squareup.picasso.Picasso


class FriendsAdapter(
    private var dataSet: List<User>,
    private val addFriend: Boolean,
    private val viewModel: UserViewModel
) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    fun setNewData(data: List<User>) {
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewAvatar: RoundedImageView = view.findViewById(R.id.roundedImageViewItemAvatar)
        var textViewName: TextView = view.findViewById(R.id.textViewItemNameFriend)
        var textViewNickname: TextView = view.findViewById(R.id.textViewItemFriendNickname)
        var imageViewAddDeleteFriend: ImageView =
            view.findViewById(R.id.imageViewItemAddDeleteFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewModel.userFriends.sent.contains(dataSet[position]))
            holder.imageViewAddDeleteFriend.visibility = View.INVISIBLE

        Picasso.get().load(dataSet[position].photoUrl).into(holder.imageViewAvatar)
        holder.textViewName.text = dataSet[position].name
        holder.textViewNickname.text = dataSet[position].nickname
        if (addFriend) {
            holder.imageViewAddDeleteFriend.setImageResource(R.drawable.ic__add)
            holder.imageViewAddDeleteFriend.setOnClickListener {
               viewModel.sendFriendRequest(viewModel.user.id, dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}