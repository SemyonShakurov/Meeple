package com.mscorp.meeple.features.core_feature

import com.mscorp.meeple.features.core_feature.api.UserApi
import com.mscorp.meeple.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import javax.inject.Inject

internal class UserRepository @Inject constructor(
    private val userApi: UserApi,
) {

    fun getAllUsers(): Single<List<User>> {
        return userApi.getAllUsers()
    }

    fun sendFriendRequest(
        id: Int,
        friendId: Int,
    ): Single<User> {
        return userApi.sendFriendRequest(
            id = id,
            friendId = friendId,
        )
    }

    // TODO: Remove MultipartBody from method arguments
    fun uploadAvatar(
        id: Int,
        file: MultipartBody.Part,
    ): Single<User> {
        return userApi.uploadAvatar(
            id = id,
            file = file,
        )
    }
}