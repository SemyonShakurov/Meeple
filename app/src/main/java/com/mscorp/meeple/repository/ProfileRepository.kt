package com.mscorp.meeple.repository

class ProfileRepository() : BaseRepository() {

    suspend fun declineFriendRequest(id: Int, friendId: Int) =
        safeApiCall { api.declineFriend(id, friendId) }

    suspend fun deleteFriendRequest(id: Int, friendId: Int) =
        safeApiCall { api.deleteFriend(id, friendId) }

    suspend fun acceptFriendRequest(id: Int, friendId: Int) =
        safeApiCall { api.acceptFriend(id, friendId) }

    suspend fun sendFriendRequest(id: Int, friendId: Int) =
        safeApiCall { api.sendFriendRequest(id, friendId) }

    suspend fun getAllUsers() =
        safeApiCall { api.getAllUsers() }

    suspend fun addGame(id: Int, gameId: Int) = safeApiCall { api.addGame(id, gameId) }

    suspend fun deleteGame(id: Int, gameId: Int) = safeApiCall { api.deleteGame(id, gameId) }
}