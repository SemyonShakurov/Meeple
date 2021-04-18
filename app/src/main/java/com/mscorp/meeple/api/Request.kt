package com.mscorp.meeple.api

import okhttp3.ResponseBody

sealed class Request<out T> {

    data class Success<out T>(val value: T) : Request<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : Request<Nothing>()

    object Loading:Request<Nothing>()
}