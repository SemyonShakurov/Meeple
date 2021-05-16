package com.mscorp.meeple.repository

class EventsRepository : BaseRepository() {

    suspend fun addEvent(
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
    ) = safeApiCall {
        api.addEvent(
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
    }

    suspend fun getEvents() = safeApiCall {
        api.getEvents()
    }
}