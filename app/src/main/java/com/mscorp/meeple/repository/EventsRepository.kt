package com.mscorp.meeple.repository

class EventsRepository : BaseRepository() {

    suspend fun addEvent(
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
    ) = safeApiCall {
        api.addEvent(
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
    }

    suspend fun getEvents() = safeApiCall {
        api.getEvents()
    }
}