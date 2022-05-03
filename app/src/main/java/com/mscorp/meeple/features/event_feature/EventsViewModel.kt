package com.mscorp.meeple.features.event_feature

import androidx.lifecycle.MutableLiveData
import com.mscorp.meeple.core.MeepleViewModel
import com.mscorp.meeple.model.Event
import com.mscorp.meeple.model.Request
import javax.inject.Inject

internal class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
) : MeepleViewModel() {

    private var events: MutableList<Event> = mutableListOf()
    val eventsLiveData: MutableLiveData<MutableList<Event>> = MutableLiveData()
    val createEventLiveData: MutableLiveData<Request<Event>> = MutableLiveData()

    fun loadEvents() = eventsRepository.getEvents().loadResource(
        onSuccess = {
            events = it.value.toMutableList()
            eventsLiveData.value = events
        },
    )

    // TODO: oh...
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
    ) = eventsRepository.addEvent(
        title = title,
        count = count,
        games = games,
        playersLevel = playersLevel,
        info = info,
        date = date,
        members = members,
        lat = lat,
        lng = lng,
        creatorId = creatorId,
    ).loadResource(
        onLoading = {
            createEventLiveData.value = it
        },
        onSuccess = {
            createEventLiveData.value = it
            events.add((createEventLiveData.value as Request.Success<Event>).value)
            eventsLiveData.value = events
        },
        onError = {
            createEventLiveData.value = it
        }
    )
}