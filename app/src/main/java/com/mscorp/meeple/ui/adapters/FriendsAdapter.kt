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
import com.makeramen.roundedimageview.RoundedImageView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.User
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class FriendsAdapter(
    var dataSet: MutableList<User>,
    private val addFriend: Boolean,
    private val viewModel: UserViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    fun setNewData(data: MutableList<User>) {
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewAvatar: RoundedImageView = view.findViewById(R.id.roundedImageViewItemAvatar)
        var textViewName: TextView = view.findViewById(R.id.textViewItemNameFriend)
        var textViewNickname: TextView = view.findViewById(R.id.textViewItemFriendNickname)
        var imageViewAddDeleteFriend: ImageView =
            view.findViewById(R.id.imageViewItemDecline)
        var constraint: ConstraintLayout = view.findViewById(R.id.constraintsFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewModel.userFriends.sent.contains(dataSet[position]))
            holder.imageViewAddDeleteFriend.visibility = View.INVISIBLE

        if (!addFriend)
            holder.constraint.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("user", dataSet[position])
                navController.navigate(
                    R.id.action_myFriendsFragment_to_friendDetailedFragment,
                    bundle
                )
            }

        Picasso.get().load(dataSet[position].photoUrl).into(holder.imageViewAvatar)
        holder.textViewName.text = dataSet[position].name
        holder.textViewNickname.text = dataSet[position].nickname
        if (addFriend) {
            holder.imageViewAddDeleteFriend.setImageResource(R.drawable.ic__add)
            if (viewModel.userFriends.declined.contains(dataSet[position])) {
                holder.imageViewAddDeleteFriend.setOnClickListener {
                    viewModel.acceptFriendRequest(viewModel.user.id, dataSet[position].id)
                }
            } else {
                holder.imageViewAddDeleteFriend.setOnClickListener {
                    viewModel.sendFriendRequest(viewModel.user.id, dataSet[position].id)
                }
            }
        } else {
            holder.imageViewAddDeleteFriend.setImageResource(R.drawable.ic__delete)
            holder.imageViewAddDeleteFriend.setOnClickListener {
                viewModel.deleteFriendRequest(viewModel.user.id, dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}