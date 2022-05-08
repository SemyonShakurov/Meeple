package com.mscorp.meeple.features.friend_feature.api

import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import javax.inject.Singleton

internal interface FriendsApi {

    @PUT("user/deleteFriend")
    fun deleteFriend(
        @Query("id") id: Int,
        @Query("friendId") friendId: Int,
    ): Single<User>

    @PUT("user/acceptRequest")
    fun acceptFriend(
        @Query("id") id: Int,
        @Query("friendId") friendId: Int,
    ): Single<User>

    @PUT("user/declineRequest")
    fun declineFriend(
        @Query("id") id: Int,
        @Query("friendId") friendId: Int,
    ): Single<User>

    @GET("user/getFriends")
    fun getFriends(@Query("id") id: Int): Single<UserFriends>
}

@Module
class FriendsProvideModule {

    @Provides
    @Singleton
    internal fun provideFriendsApi(retrofit: Retrofit): FriendsApi {
        return retrofit.create(FriendsApi::class.java)
    }
}