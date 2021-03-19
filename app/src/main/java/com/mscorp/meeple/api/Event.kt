package com.mscorp.meeple.api

import okhttp3.ResponseBody

sealed class Event<out T> {
    data class Success<out T>(val value: T) : Event<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Event<Nothing>()

    object Loading:Event<Nothing>()
}