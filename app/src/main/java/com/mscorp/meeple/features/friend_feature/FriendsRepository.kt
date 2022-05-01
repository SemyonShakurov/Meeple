package com.mscorp.meeple.features.friend_feature

import com.mscorp.meeple.features.friend_feature.api.FriendsApi
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class FriendsRepository @Inject constructor(
    private val friendsApi: FriendsApi,
) {

    fun deleteFriend(
        id: Int,
        friendId: Int,
    ): Single<User> {
        return friendsApi.deleteFriend(
            id = id,
            friendId = friendId,
        )
    }

    fun acceptFriend(
        id: Int,
        friendId: Int,
    ): Single<User> {
        return friendsApi.acceptFriend(
            id = id,
            friendId = friendId,
        )
    }

    fun declineFriend(
        id: Int,
        friendId: Int,
    ): Single<User> {
        return friendsApi.declineFriend(
            id = id,
            friendId = friendId,
        )
    }

    fun getFriends(id: Int): Single<UserFriends> {
        return friendsApi.getFriends(id = id)
    }
}