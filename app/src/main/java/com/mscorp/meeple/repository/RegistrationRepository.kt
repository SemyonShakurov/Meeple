package com.mscorp.meeple.repository

import java.lang.Exception

class RegistrationRepository : BaseRepository() {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ) = safeApiCall {
        val res = api.register(email, username, password);
        //TODO: Проверка данных
        res
    }


}