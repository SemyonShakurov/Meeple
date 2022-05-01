package com.mscorp.meeple.api

import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Event
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Singleton

internal interface GamesApi {

    @PUT("events/subscribeToEvent")
    fun subscribeToEvent(
        @Query("eventId") eventId: Int,
        @Query("userId") userId: Int,
    ): Single<Event>

    @PUT("events/unsubscribeFromEvent")
    fun unsubscribeFromEvent(
        @Query("eventId") eventId: Int,
        @Query("userId") userId: Int,
    ): Single<Event>

    @GET("events/getAll")
    fun getEvents(): Single<List<Event>>

    @GET("games/getAll")
    fun getAllGames(): Single<List<BoardGame>>

    @PUT("games/addGame")
    fun addGame(@Query("userId") id: Int, @Query("gameId") gameID: Int): Single<BoardGame>

    @PUT("games/removeGame")
    fun deleteGame(
        @Query("userId") id: Int,
        @Query("gameId") gameID: Int,
    ): Single<BoardGame>

    @FormUrlEncoded
    @POST("events/addEvent")
    fun addEvent(
        @Field("title") title: String,
        @Field("count") count: String,
        @Field("games") games: List<Int>,
        @Field("playersLevel") playersLevel: Int,
        @Field("info") info: String,
        @Field("date") date: Long,
        @Field("members") members: List<Int>,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("creatorId") creatorId: Int,
    ): Single<Event>
}

@Module
abstract class GamesProvideModule {

    @Provides
    @Singleton
    internal fun provideGamesApi(retrofit: Retrofit): GamesApi {
        return retrofit.create(GamesApi::class.java)
    }
}