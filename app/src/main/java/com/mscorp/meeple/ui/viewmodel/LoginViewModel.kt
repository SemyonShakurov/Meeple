package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.api.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.repository.AuthRepository
import kotlinx.coroutines.launch


class LoginViewModel : BaseViewModel() {

    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()
    private val loginRepository = AuthRepository()

    fun login(
        nickname: String,
        password: String
    ) =
        viewModelScope.launch {
            loginResponse.value = Request.Loading
            loginResponse.value = loginRepository.login(nickname, password)
        }

}