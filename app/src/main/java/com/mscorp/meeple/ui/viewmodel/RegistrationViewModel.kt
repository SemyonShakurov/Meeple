package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.model.User
import com.mscorp.meeple.repository.RegistrationRepository
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel() {

    val loginResponse: MutableLiveData<Event<User>> = MutableLiveData()
    private val regRepository = RegistrationRepository()

    fun register(
        email: String,
        username: String,
        password: String
    ){
        viewModelScope.launch{
            loginResponse.value = Event.Loading
            loginResponse.value = regRepository.register(email, username, password)
        }
    }
}