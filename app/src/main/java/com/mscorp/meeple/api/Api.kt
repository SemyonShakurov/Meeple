package com.mscorp.meeple.api

import com.mscorp.meeple.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("/SEMA_LOX/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : User

    @FormUrlEncoded
    @POST("/SEMA_LOX_NEW/")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nickname") nickname: String
    ) : User

}