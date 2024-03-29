package com.mscorp.meeple.api

import com.mscorp.meeple.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    //Auth
    @GET("login/get")
    suspend fun login(
        @Query("nickname") email: String,
        @Query("password") password: String
    ): User

    //Friends
    @PUT("user/deleteFriend")
    suspend fun deleteFriend(@Query("id") id: Int, @Query("friendId") friendId: Int): User

    @PUT("user/acceptRequest")
    suspend fun acceptFriend(@Query("id") id: Int, @Query("friendId") friendId: Int): User

    @PUT("user/declineRequest")
    suspend fun declineFriend(@Query("id") id: Int, @Query("friendId") friendId: Int): User

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

    @FormUrlEncoded
    @POST("events/addEvent")
    suspend fun addEvent(
        @Field("title") title: String,
        @Field("count") count: String,
        @Field("games") games: List<Int>,
        @Field("playersLevel") playersLevel: Int,
        @Field("info") info: String,
        @Field("date") date: Long,
        @Field("members") members: List<Int>,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("creatorId") creatorId: Int
    ): Event

    @GET("events/getAll")
    suspend fun getEvents(): List<Event>

    @FormUrlEncoded
    @PUT("register/confirmEmail")
    suspend fun confirmEmail(
        @Field("email") email: String,
        @Field("code") code: Int
    ): User

    @FormUrlEncoded
    @PUT("register/sendCode")
    suspend fun sendCode(
        @Field("nickname") nickname: String
    ): User

    @FormUrlEncoded
    @PUT("register/resetPassword")
    suspend fun resetPassword(
        @Field("id") id: Int,
        @Field("password") password: String
    ): User

    @GET("games/getAll")
    suspend fun getAllGames(): List<BoardGame>

    @PUT("games/addGame")
    suspend fun addGame(@Query("userId") id: Int, @Query("gameId") gameID: Int): BoardGame

    @PUT("games/removeGame")
    suspend fun deleteGame(@Query("userId") id: Int, @Query("gameId") gameID: Int): BoardGame

    @Multipart
    @POST("user/uploadAvatar")
    suspend fun uploadAvatar(
        @Query("id") id: Int,
        @Part file: MultipartBody.Part,
    ): User


    @PUT("events/subscribeToEvent")
    suspend fun subscribeToEvent(
        @Query("eventId") eventId: Int,
        @Query("userId") userId: Int
    ): Event

    @PUT("events/unsubscribeFromEvent")
    suspend fun unsubscribeFromEvent(
        @Query("eventId") eventId: Int,
        @Query("userId") userId: Int
    ): Event

}