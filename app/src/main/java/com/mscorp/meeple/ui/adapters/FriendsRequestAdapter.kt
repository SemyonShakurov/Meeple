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

class FriendsRequestAdapter(
    private var dataSet: List<User>,
    private val viewModel: UserViewModel
) : RecyclerView.Adapter<FriendsRequestAdapter.ViewHolder>() {

    fun setNewData(data: List<User>) {
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewAvatar: RoundedImageView = view.findViewById(R.id.roundedImageViewItemAvatar)
        var textViewName: TextView = view.findViewById(R.id.textViewItemNameFriend)
        var textViewNickname: TextView = view.findViewById(R.id.textViewItemFriendNickname)
        var imageViewAccept: ImageView = view.findViewById(R.id.imageViewItemAccept)
        var imageViewDecline: ImageView = view.findViewById(R.id.imageViewItemDecline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(dataSet[position].photoUrl).into(holder.imageViewAvatar)
        holder.textViewName.text = dataSet[position].name
        holder.textViewNickname.text = dataSet[position].nickname

        holder.imageViewAccept.setOnClickListener{
            viewModel.acceptFriendRequest(viewModel.user.id, dataSet[position].id)
        }

        holder.imageViewDecline.setOnClickListener{
            viewModel.declineFriendRequest(viewModel.user.id, dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size
}