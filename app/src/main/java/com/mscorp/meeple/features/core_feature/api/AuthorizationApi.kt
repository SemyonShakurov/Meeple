package com.mscorp.meeple.features.core_feature.api

import com.mscorp.meeple.model.User
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Singleton

internal interface AuthorizationApi {

    @GET("login/get")
    fun login(
        @Query("nickname") email: String,
        @Query("password") password: String,
    ): Single<User>

    @FormUrlEncoded
    @PUT("register/confirmEmail")
    fun confirmEmail(
        @Field("email") email: String,
        @Field("code") code: Int,
    ): Single<User>

    @FormUrlEncoded
    @PUT("register/sendCode")
    fun sendCode(
        @Field("nickname") nickname: String,
    ): Single<User>

    @FormUrlEncoded
    @PUT("register/resetPassword")
    fun resetPassword(
        @Field("id") id: Int,
        @Field("password") password: String,
    ): Single<User>

    @FormUrlEncoded
    @POST("register/add")
    fun register(
        @Field("name") name: String,
        @Field("nickname") nickname: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Single<User>
}

@Module
abstract class AuthorizationProvideModule {

    @Provides
    @Singleton
    internal fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi {
        return retrofit.create(AuthorizationApi::class.java)
    }
}