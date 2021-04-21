package com.mscorp.meeple.api

import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import retrofit2.http.*

interface Api {
    @GET("login/get")
    suspend fun login(
        @Query("nickname") email: String,
        @Query("password") password: String
    ): User

    @GET("")
    suspend fun updateGames(): List<BoardGame>

    @PUT("user/acceptRequest")
    suspend fun acceptFriend(@Query("id") id:Int, @Query("friendId") friendId: Int ):User

    @GET("user/getFriends")
    suspend fun getFriends(@Query("id") id: Int): UserFriends

    @GET("user/getAll")
    suspend fun getAllUsers(): MutableList<User>

    @FormUrlEncoded
    @PUT("user/sendRequest")
    suspend fun sendFriendRequest(
        @Field("id") id: Int,
        @Field("friendId") friendId: Int
    ): User

    @FormUrlEncoded
    @POST("register/add")
    suspend fun register(
        @Field("name") name: String,
        @Field("nickname") nickname: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): User

}