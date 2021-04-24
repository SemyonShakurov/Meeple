package com.mscorp.meeple.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mscorp.meeple.model.Event

class EventsCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}