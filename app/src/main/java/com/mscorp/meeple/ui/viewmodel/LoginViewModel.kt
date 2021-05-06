package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.repository.AuthRepository
import kotlinx.coroutines.launch


class LoginViewModel :  ViewModel() {

    val gamesResponse: MutableLiveData<Request<List<BoardGame>>> = MutableLiveData()
    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()
    val friendsResponse: MutableLiveData<Request<UserFriends>> = MutableLiveData()
    private val loginRepository = AuthRepository()

    fun getAllGames(){
        viewModelScope.launch {
            gamesResponse.value = Request.Loading
            gamesResponse.value = loginRepository.getAllGames()
        }
    }

    fun getFriends(id: Int){
        viewModelScope.launch {
            friendsResponse.value = Request.Loading
            friendsResponse.value = loginRepository.getFriends(id)
        }
    }

    fun login(
        nickname: String,
        password: String
    ) =
        viewModelScope.launch {
            loginResponse.value = Request.Loading
            loginResponse.value = loginRepository.login(nickname, password)
        }

}