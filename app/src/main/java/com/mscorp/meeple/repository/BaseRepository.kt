package com.mscorp.meeple.repository

import com.mscorp.meeple.api.ApiService
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    val api = ApiService.getApi()

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Event<T>{
        //Переходим корутину в паралдлельный поток
        return withContext(Dispatchers.IO){
            try {
                //Working version
                Event.Success(apiCall.invoke())
            }
            catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Event.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Event.Failure(true, null, null)
                    }
                }
            }
        }
    }
}