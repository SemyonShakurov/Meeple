package com.mscorp.meeple.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.model.User
import com.mscorp.meeple.repository.LoginRepository
import kotlinx.coroutines.launch

class AuthorizationViewModel : BaseViewModel() {

    val loginResponse: MutableLiveData<Event<User>> = MutableLiveData()
    private val loginRepository = LoginRepository()


    fun login(
        email: String,
        password: String
    ) =
        viewModelScope.launch {
            loginResponse.value = Event.Loading
            loginResponse.value = loginRepository.login(email, password)
        }

}