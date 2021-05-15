package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.model.*
import com.mscorp.meeple.repository.ProfileRepository
import kotlinx.coroutines.launch
import java.io.File

class UserViewModel : ViewModel() {
    lateinit var user: User
    lateinit var userFriends: UserFriends
    lateinit var games: List<BoardGame>

    private var repository = ProfileRepository()

    var getAllUsersResponse: MutableLiveData<Request<MutableList<User>>> = MutableLiveData()
    var sendFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var acceptFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var deleteFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var declineFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var addGameResponse: MutableLiveData<Request<BoardGame>> = MutableLiveData()
    var deleteGameResponse: MutableLiveData<Request<BoardGame>> = MutableLiveData()

    fun addGameRequest(id: Int, gameId: Int) {
        viewModelScope.launch {
            addGameResponse.value = Request.Loading
            addGameResponse.value = repository.addGame(id, gameId)
        }
    }

    fun deleteGameRequest(id: Int, gameId: Int) {
        viewModelScope.launch {
            deleteGameResponse.value = Request.Loading
            deleteGameResponse.value = repository.deleteGame(id, gameId)
        }
    }

    fun declineFriendRequest(id: Int, friendId: Int) {
        viewModelScope.launch {
            declineFriendRequestResponse.value = Request.Loading
            declineFriendRequestResponse.value = repository.declineFriendRequest(id, friendId)
        }
    }

    fun acceptFriendRequest(id: Int, friendId: Int) {
        viewModelScope.launch {
            acceptFriendRequestResponse.value = Request.Loading
            acceptFriendRequestResponse.value = repository.acceptFriendRequest(id, friendId)
        }
    }

    fun deleteFriendRequest(id: Int, friendId: Int) {
        viewModelScope.launch {
            deleteFriendRequestResponse.value = Request.Loading
            deleteFriendRequestResponse.value = repository.deleteFriendRequest(id, friendId)
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            getAllUsersResponse.value = Request.Loading
            getAllUsersResponse.value = repository.getAllUsers()
        }
    }

    fun sendFriendRequest(id: Int, friendId: Int) {
        viewModelScope.launch {
            sendFriendRequestResponse.value = Request.Loading
            sendFriendRequestResponse.value = repository.sendFriendRequest(id, friendId)
        }
    }

    fun getUsersGames(): List<BoardGame> {
        return if (user.games == null)
            listOf()
        else
            games.filter { user.games!!.contains(it.id) }
    }

    fun getNotUsersGames(): List<BoardGame> {
        return if (user.games == null)
            games
        else
            games.filter { !user.games!!.contains(it.id) }
    }


    fun uploadAvatar(file: File, body: UploadRequestBody) {
        viewModelScope.launch {
            val request: Request<User> =  repository.uploadAvatar(user.id, file, body)
            if (request is Request.Success) {
                user = request.value
            }
        }
    }
}