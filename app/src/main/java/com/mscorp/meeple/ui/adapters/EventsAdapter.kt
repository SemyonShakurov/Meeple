package com.mscorp.meeple.ui.adapters

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.Event

class EventsAdapter(
    context: Context,
    private val navController: NavController
) : ListAdapter<Event, EventsAdapter.EventsViewHolder>(EventsCallback()) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(inflater.inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.cardView.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("event", getItem(position) as Event)
            navController.navigate(R.id.action_navigation_events_to_eventFragment, bundle)
        }
    }

    class EventsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTextView = view.findViewById<TextView>(R.id.textViewEventTitle)
        private val dateTextView = view.findViewById<TextView>(R.id.textViewEventDate)
        val cardView: CardView = view.findViewById(R.id.cardViewEvent)

        fun bind(event: Event) {
            titleTextView.text = event.title
            dateTextView.text = DateFormat.format("dd.MM.yyyy; HH:mm", event.date).toString()
        }
    }
}