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
    val createEventLiveData: MutableLiveData<Request<Event>> = MutableLiveData()

    fun loadEvents() {
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
        count: String,
        games: List<Int>,
        playersLevel: Int,
        info: String,
        date: Long,
        members: List<Int>,
        lat: Double,
        lng: Double,
        creatorId: Int
    ) {
        viewModelScope.launch {
            createEventLiveData.value = Request.Loading
            createEventLiveData.value = eventsRepository.addEvent(
                title,
                count,
                games,
                playersLevel,
                info,
                date,
                members,
                lat,
                lng,
                creatorId
            )
            if (createEventLiveData.value is Request.Success) {
                events.add((createEventLiveData.value as Request.Success<Event>).value)
                eventsLiveData.value= events
            }
        }
    }
}