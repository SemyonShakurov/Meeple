package com.mscorp.meeple.features.games_feature

import com.mscorp.meeple.features.games_feature.api.GamesApi
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Event
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class GamesRepository @Inject constructor(
    private val gamesApi: GamesApi,
) {

    fun subscribeToEvent(
        eventId: Int,
        userId: Int,
    ): Single<Event> {
        return gamesApi.subscribeToEvent(
            eventId = eventId,
            userId = userId,
        )
    }

    fun unsubscribeFromEvent(
        eventId: Int,
        userId: Int,
    ): Single<Event> {
        return gamesApi.unsubscribeFromEvent(
            eventId = eventId,
            userId = userId,
        )
    }

    fun getEvents(): Single<List<Event>> {
        return gamesApi.getEvents()
    }

    fun getAllGames(): Single<List<BoardGame>> {
        return gamesApi.getAllGames()
    }

    fun addGame(
        id: Int,
        gameId: Int,
    ): Single<BoardGame> {
        return gamesApi.addGame(
            id = id,
            gameId = gameId,
        )
    }

    fun deleteGame(
        id: Int,
        gameId: Int,
    ): Single<BoardGame> {
        return gamesApi.deleteGame(
            id = id,
            gameId = gameId,
        )
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
        creatorId: Int,
    ): Single<Event> {
        return gamesApi.addEvent(
            title = title,
            count = count,
            games = games,
            playersLevel = playersLevel,
            info = info,
            date = date,
            members = members,
            lat = lat,
            lng = lng,
            creatorId = creatorId
        )
    }
}