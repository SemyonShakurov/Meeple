package com.mscorp.meeple.features.event_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.databinding.FragmentEventsBinding
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel
import com.mscorp.meeple.model.Event
import com.mscorp.meeple.ui.adapters.EventsAdapter

internal class EventsFragment : MeepleFragment<UserViewModel>(), Observer<List<Event>> {

    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel = createViewModel()
        adapter = EventsAdapter(requireActivity(), findNavController())
    }

    override fun onStart() {
        super.onStart()
        eventsViewModel.eventsLiveData.observe(this, this)
        eventsViewModel.loadEvents()
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
        if (t != null) {
            adapter.submitList(t.filter {
                it.members.map { user -> user.id }.contains(viewModel.user.id)
            })
        }
    }
}