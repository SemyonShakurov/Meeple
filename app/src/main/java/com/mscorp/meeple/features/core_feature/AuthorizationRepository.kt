package com.mscorp.meeple.features.core_feature

import android.content.SharedPreferences
import com.mscorp.meeple.features.core_feature.api.AuthorizationApi
import com.mscorp.meeple.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class AuthorizationRepository @Inject constructor(
    private val authorizationApi: AuthorizationApi,
    private val prefs: SharedPreferences,
) {

    fun login(
        nickname: String,
        password: String
    ): Single<User> {
        return authorizationApi
            .login(email = nickname, password = password)
            .doOnSuccess {
                prefs.edit()
                    .putString("login", nickname)
                    .putString("password", password)
                    .apply()
            }
    }

    fun confirmEmail(
        email: String,
        code: Int,
    ): Single<User> {
        return authorizationApi.confirmEmail(
            email = email,
            code = code,
        )
    }

    fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ): Single<User> {
        return authorizationApi.register(
            name = name,
            nickname = username,
            email = email,
            password = password
        )
    }

    fun sendCode(nickname: String): Single<User> {
        return authorizationApi.sendCode(nickname = nickname)
    }

    fun resetPassword(
        id: Int,
        password: String,
    ): Single<User> {
        return authorizationApi.resetPassword(
            id = id,
            password = password,
        ).doOnSuccess {
            prefs.edit()
                .clear()
                .apply()
        }
    }
}