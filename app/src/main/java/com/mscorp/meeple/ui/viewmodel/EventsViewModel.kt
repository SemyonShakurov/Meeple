package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.model.Event
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.repository.EventsRepository
import kotlinx.coroutines.launch

class EventsViewModel :  ViewModel() {

    private val eventsRepository = EventsRepository()
    private var events: MutableList<Event> = mutableListOf()
    val eventsLiveData: MutableLiveData<MutableList<Event>> = MutableLiveData()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            val request: Request<List<Event>> = eventsRepository.getEvents()
            if (request is Request.Success) {
                events = request.value.toMutableList()
                eventsLiveData.value = events
            }
        }
    }

    fun addEvent(
        title: String,
        count: Int,
        games: List<Int>,
        playersLevel: Int,
        type: Int,
        info: String,
        date: Int,
        access: Int,
        members: List<Int>,
        creatorId: Int
    ) {
        viewModelScope.launch {
            val request: Request<Event> = eventsRepository.addEvent(
                title,
                count,
                games,
                playersLevel,
                type,
                info,
                date,
                access,
                members,
                creatorId
            )
            if (request is Request.Success) {
                events.add(request.value)
                eventsLiveData.value = events
            }
        }
    }
}