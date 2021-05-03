package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.repository.ProfileRepository
import kotlinx.coroutines.launch

class UserViewModel :BaseViewModel() {
    lateinit var user: User
    lateinit var userFriends: UserFriends
    private  var repository = ProfileRepository()

    var getAllUsersResponse: MutableLiveData<Request<MutableList<User>>> = MutableLiveData()
    var sendFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var acceptFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var deleteFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()
    var declineFriendRequestResponse: MutableLiveData<Request<User>> = MutableLiveData()

    fun declineFriendRequest(id: Int, friendId: Int){
        viewModelScope.launch {
            declineFriendRequestResponse.value = Request.Loading
            declineFriendRequestResponse.value = repository.declineFriendRequest(id, friendId)
        }
    }

    fun acceptFriendRequest(id: Int, friendId: Int){
        viewModelScope.launch {
            acceptFriendRequestResponse.value = Request.Loading
            acceptFriendRequestResponse.value = repository.acceptFriendRequest(id, friendId)
        }
    }

    fun deleteFriendRequest(id: Int, friendId: Int){
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

}