package com.mscorp.meeple.repository

import java.lang.Exception

class LoginRepository : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        val res = api.login();
        res
    }
}