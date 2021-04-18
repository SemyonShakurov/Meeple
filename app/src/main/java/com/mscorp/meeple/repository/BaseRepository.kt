package com.mscorp.meeple.repository

import com.google.gson.Gson
import com.mscorp.meeple.api.ApiService
import com.mscorp.meeple.api.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    val api = ApiService.getApi()

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Request<T> =
        //Переходим корутину в паралдлельный поток
         withContext(Dispatchers.IO){
            try {
                val x = apiCall.invoke()
                Request.Success(x)
            }
            catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val mesJson = throwable.response()?.errorBody()?.string()
                        var message = "Some problems"
                        mesJson?.let {
                            message = JSONObject(mesJson).getString("message")
                        }
                        Request.Failure(false, throwable.code(), message)
                    }
                    else -> {
                        Request.Failure(true, null, null)
                    }
                }
            }
        }
}