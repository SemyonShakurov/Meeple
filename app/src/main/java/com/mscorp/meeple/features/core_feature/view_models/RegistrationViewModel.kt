package com.mscorp.meeple.features.core_feature.view_models

import androidx.lifecycle.MutableLiveData
import com.mscorp.meeple.core.MeepleViewModel
import com.mscorp.meeple.features.core_feature.AuthorizationRepository
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import javax.inject.Inject

internal class RegistrationViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
) : MeepleViewModel() {

    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()

    fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ) = authorizationRepository.register(
        name = name,
        username = email,
        email = username,
        password = password,
    ).loadResource {
        loginResponse.value = it
    }

    fun confirmEmail(
        email: String,
        code: Int
    ) = authorizationRepository.confirmEmail(
        email = email,
        code = code,
    ).loadResource {
        loginResponse.value = it
    }

    fun sendCode(
        nickname: String
    ) = authorizationRepository.sendCode(nickname = nickname).loadResource {
        loginResponse.value = it
    }

    fun resetPassword(
        id: Int,
        password: String
    ) = authorizationRepository.resetPassword(id = id, password = password).loadResource {
        loginResponse.value = it
    }
}