package com.mscorp.meeple.repository

class AuthRepository : BaseRepository() {

    suspend fun login(
        nickname: String,
        password: String
    ) = safeApiCall {
        api.login(nickname, password)
    }

    suspend fun register(
        email: String,
        username: String,
        password: String
    ) = safeApiCall {
        val res = api.register(email, username, password)
        //TODO: Проверка данных
        res
    }
}