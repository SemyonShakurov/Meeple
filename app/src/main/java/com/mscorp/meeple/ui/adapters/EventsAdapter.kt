package com.mscorp.meeple.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(
    context: Context
) : ListAdapter<Event, EventsAdapter.EventsViewHolder>(EventsCallback()) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(inflater.inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTextView = view.findViewById<TextView>(R.id.textViewEventTitle)
        private val dateTextView = view.findViewById<TextView>(R.id.textViewEventDate)

        fun bind(event: Event) {
            titleTextView.text = event.title
            val date = Date(event.date.toLong() * 1000L)
            dateTextView.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US).format(date)

        }
    }

}