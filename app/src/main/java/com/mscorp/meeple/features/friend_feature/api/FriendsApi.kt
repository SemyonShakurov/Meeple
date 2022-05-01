package com.mscorp.meeple.features.friend_feature.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

internal interface FriendsApi {


}

@Module
abstract class FriendsProvideModule {

    @Provides
    @Singleton
    internal fun provideFriendsApi(retrofit: Retrofit): FriendsApi {
        return retrofit.create(FriendsApi::class.java)
    }
}