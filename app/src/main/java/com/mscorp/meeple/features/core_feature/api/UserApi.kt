package com.mscorp.meeple.features.core_feature.api

import com.mscorp.meeple.model.User
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Singleton

internal interface UserApi {

    @GET("user/getAll")
    fun getAllUsers(): Single<List<User>>

    @FormUrlEncoded
    @PUT("user/sendRequest")
    fun sendFriendRequest(
        @Field("id") id: Int,
        @Field("friendId") friendId: Int,
    ): Single<User>

    @Multipart
    @POST("user/uploadAvatar")
    fun uploadAvatar(
        @Query("id") id: Int,
        @Part file: MultipartBody.Part,
    ): Single<User>
}

@Module
abstract class UserProvideModule {

    @Provides
    @Singleton
    internal fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}