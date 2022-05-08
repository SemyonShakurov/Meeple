package com.mscorp.meeple.features.core_feature.view_models

import androidx.lifecycle.MutableLiveData
import com.mscorp.meeple.core.MeepleViewModel
import com.mscorp.meeple.features.core_feature.UserRepository
import com.mscorp.meeple.features.event_feature.EventsRepository
import com.mscorp.meeple.features.friend_feature.FriendsRepository
import com.mscorp.meeple.features.games_feature.GamesRepository
import com.mscorp.meeple.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


internal class UserViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val gamesRepository: GamesRepository,
    private val friendRepository: FriendsRepository,
    private val userRepository: UserRepository,
) : MeepleViewModel() {

    lateinit var user: User
    lateinit var userFriends: UserFriends
    lateinit var games: List<BoardGame>

    var getAllUsersResponse: MutableLiveData<Request<List<User>>> = MutableLiveData()
    var sendFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var acceptFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var deleteFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var declineFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var addGameResponse: MutableLiveData<Request<BoardGame>> = MutableLiveData()
    var deleteGameResponse: MutableLiveData<Request<BoardGame>> = MutableLiveData()
    var subscribeEventResponse: MutableLiveData<Request<Event>> = MutableLiveData()
    var unsubscribeEventResponse: MutableLiveData<Request<Event>> = MutableLiveData()

    fun subscribeEvent(eventId: Int, userId: Int) = eventsRepository.subscribeToEvent(
        eventId = eventId,
        userId = userId,
    ).loadResource { subscribeEventResponse.value = it }

    fun unsubscribeEvent(eventId: Int, userId: Int) = eventsRepository.unsubscribeFromEvent(
        eventId = eventId,
        userId = userId,
    ).loadResource { unsubscribeEventResponse.value = it }

    fun addGameRequest(id: Int, gameId: Int) =
        gamesRepository.addGame(id = id, gameId = gameId).loadResource {
            addGameResponse.value = it
        }

    fun deleteGameRequest(id: Int, gameId: Int) = gamesRepository.deleteGame(
        id = id,
        gameId = gameId,
    ).loadResource {
        deleteGameResponse.value = it
    }

    fun declineFriendRequest(id: Int, friendId: Int) =
        friendRepository.declineFriend(id = id, friendId = friendId).loadResource {
            declineFriendRequestResponse.value = it
        }

    fun acceptFriendRequest(id: Int, friendId: Int) =
        friendRepository.acceptFriend(id = id, friendId = friendId).loadResource {
            acceptFriendRequestResponse.value = it
        }

    fun deleteFriendRequest(id: Int, friendId: Int) = friendRepository.deleteFriend(
        id = id, friendId = friendId,
    ).loadResource {
        deleteFriendRequestResponse.value = it
    }

    fun getAllUsers() = userRepository.getAllUsers().loadResource {
        getAllUsersResponse.value = it
    }

    fun sendFriendRequest(id: Int, friendId: Int) = userRepository.sendFriendRequest(
        id = id,
        friendId = friendId,
    ).loadResource {
        sendFriendRequestResponse.value = it
    }

    fun getUsersGames(): List<BoardGame> {
        return if (user.games == null) {
            emptyList()
        } else {
            games.filter { user.games!!.contains(it.id) }
        }
    }

    fun getNotUsersGames(): List<BoardGame> {
        return if (user.games == null) {
            games
        } else {
            games.filter { !user.games!!.contains(it.id) }
        }
    }

    fun uploadAvatar(file: File) {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name,
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        )

        userRepository.uploadAvatar(
            id = user.id,
            file = filePart,
        ).loadResource {
            if (it is Request.Success) {
                user = it.value
            }
        }
    }
}