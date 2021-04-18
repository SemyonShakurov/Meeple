package com.mscorp.meeple.api

import com.mscorp.meeple.model.User
import retrofit2.http.*

interface Api {
    @GET("login/get")
    suspend fun login(
        @Query("nickname") email: String,
        @Query("password") password: String
    ) : User

    @GET("user")
    suspend fun getUser(
        @Field("id") id: Int
    ) : User

    @FormUrlEncoded
    @POST("register/add")
    suspend fun register(
        @Field("nickname") nickname: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : User

}