package com.mscorp.meeple.api

import com.mscorp.meeple.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.UnknownServiceException

interface Api {
    @GET("user")
    suspend fun login(
/*        @Field("email") email: String,
        @Field("password") password: String*/
    ) : User

    @GET("user")
    suspend fun getUser(
        @Field("id") id: Int
    ) : User

    @FormUrlEncoded
    @POST("user")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nickname") nickname: String
    ) : User

}