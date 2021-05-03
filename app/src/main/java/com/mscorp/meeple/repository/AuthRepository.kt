package com.mscorp.meeple.repository

class AuthRepository : BaseRepository() {

    suspend fun login(
        nickname: String,
        password: String
    ) = safeApiCall {
        api.login(nickname, password)
    }

    suspend fun getFriends(
        id: Int
    ) = safeApiCall {
        api.getFriends(id)
    }

    suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ) = safeApiCall {
        val res = api.register(name, username, email, password)
        //TODO: Проверка данных
        res
    }

    suspend fun confirmEmail(
        email: String,
        code: Int
    ) = safeApiCall {
        api.confirmEmail(email, code)
    }

    suspend fun sendCode(
        nickname: String
    ) = safeApiCall {
        api.sendCode(nickname)
    }

    suspend fun resetPassword(
        id: Int,
        password: String
    ) = safeApiCall {
        api.resetPassword(id, password)
    }
}