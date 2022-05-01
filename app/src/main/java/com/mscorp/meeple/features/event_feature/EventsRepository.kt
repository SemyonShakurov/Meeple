package com.mscorp.meeple.features.event_feature

import com.mscorp.meeple.features.event_feature.api.EventsApi
import com.mscorp.meeple.model.Event
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class EventsRepository @Inject constructor(
    private val eventsApi: EventsApi,
) {

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
    ): Single<Event> {
        return eventsApi.addEvent(
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
        )
    }

    fun getEvents(): Single<List<Event>> {
        return eventsApi.getEvents()
    }

    fun unsubscribeFromEvent(
        eventId: Int,
        userId: Int,
    ): Single<Event> {
        return eventsApi.unsubscribeFromEvent(
            eventId = eventId,
            userId = userId,
        )
    }

    fun subscribeToEvent(
        eventId: Int,
        userId: Int,
    ): Single<Event> {
        return eventsApi.subscribeToEvent(
            eventId = eventId,
            userId = userId,
        )
    }
}