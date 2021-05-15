package com.mscorp.meeple.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentEventsBinding
import com.mscorp.meeple.model.Event
import com.mscorp.meeple.ui.adapters.EventsAdapter
import com.mscorp.meeple.ui.viewmodel.EventsViewModel

class EventsFragment : Fragment(), Observer<List<Event>> {

    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsViewModel: EventsViewModel

    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        adapter = EventsAdapter(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        eventsViewModel.eventsLiveData.observe(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater)

        binding.imageAddEvent.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_events_to_addNewEventFragment)
        }
        binding.eventsList.adapter = adapter
        return binding.root
    }

    override fun onChanged(t: List<Event>?) {
        adapter.submitList(t)
    }
}