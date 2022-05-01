package com.mscorp.meeple.api

import com.mscorp.meeple.model.Event
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Singleton

internal interface EventsApi {

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
abstract class EventsProvideModule {

    @Provides
    @Singleton
    internal fun provideEventsApi(retrofit: Retrofit): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }
}