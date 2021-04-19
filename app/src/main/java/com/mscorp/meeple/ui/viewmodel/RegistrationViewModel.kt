package com.mscorp.meeple.ui.viewmodel

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mscorp.meeple.api.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.repository.AuthRepository
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel() {

    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()
    private val regRepository = AuthRepository()

  /*  fun approveFields(nickname: String, email: String, pass: String, pass2: String) : String? {
        if (!pass.equals(pass2))

    }*/

    fun register(
        email: String,
        username: String,
        password: String
    ){
        viewModelScope.launch{
            loginResponse.value = Request.Loading
            loginResponse.value = regRepository.register(email, username, password)
        }
    }
}