package com.mscorp.meeple.repository

import android.content.Context
import com.mscorp.meeple.db.GamesDatabase
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Request

class ProfileRepository() : BaseRepository() {

   /* val db = GamesDatabase.getInstance(context)
    suspend fun updateGames(): List<BoardGame>? {
        try {
                val games = safeApiCall { api.updateGames() }
                val g: List<BoardGame> = (games as Request.Success<List<BoardGame>>).value
                db?.gamesDao()?.insertAllGames(g)
        } catch (exeption: Exception){}
        return db?.gamesDao()?.getAllGames()
    }*/

    suspend fun sendFriendRequest(
        id: Int,
        friendId: Int
    ) = safeApiCall {
        api.sendFriendRequest(id, friendId)
    }

    suspend fun getAllUsers(
    ) = safeApiCall {
        api.getAllUsers()
    }
}