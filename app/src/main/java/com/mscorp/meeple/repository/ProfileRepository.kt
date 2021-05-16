package com.mscorp.meeple.repository

import com.mscorp.meeple.model.UploadRequestBody
import okhttp3.MultipartBody
import retrofit2.http.Query
import java.io.File

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

    suspend fun uploadAvatar(
        id: Int,
        file: File,
        body: UploadRequestBody
    ) = safeApiCall {
        api.uploadAvatar(
            id,
            MultipartBody.Part.createFormData("file", file.name, body)
        )
    }

    suspend fun unsubscribeFromEvent(
        eventId: Int,
        userId: Int
    ) = safeApiCall {
        api.unsubscribeFromEvent(eventId, userId)
    }

    suspend fun subscribeToEvent(
        eventId: Int,
        userId: Int
    ) = safeApiCall {
        api.subscribeToEvent(eventId, userId)
    }
}