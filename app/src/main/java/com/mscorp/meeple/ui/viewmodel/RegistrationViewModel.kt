package com.mscorp.meeple.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.repository.AuthRepository
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel() {

    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()
    private val regRepository = AuthRepository()


    fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ){
        viewModelScope.launch{
            loginResponse.value = Request.Loading
            loginResponse.value = regRepository.register(name, username, email, password)
        }
    }
}