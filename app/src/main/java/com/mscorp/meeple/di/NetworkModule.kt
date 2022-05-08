package com.mscorp.meeple.di

import android.content.Context
import android.content.SharedPreferences
import com.mscorp.meeple.core.MeepleApplication
import com.mscorp.meeple.features.core_feature.api.AuthorizationProvideModule
import com.mscorp.meeple.features.core_feature.api.UserProvideModule
import com.mscorp.meeple.features.event_feature.api.EventsProvideModule
import com.mscorp.meeple.features.friend_feature.api.FriendsProvideModule
import com.mscorp.meeple.features.games_feature.api.GamesProvideModule
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(
    includes = [
        AuthorizationProvideModule::class,
        FriendsProvideModule::class,
        EventsProvideModule::class,
        GamesProvideModule::class,
        UserProvideModule::class,
    ]
)
internal class NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(logInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePrefs(app: MeepleApplication): SharedPreferences {
        return app.getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
    }

    companion object {

        private const val BASE_URL = "http://meeple-cloud.ru:8080/"
    }
}