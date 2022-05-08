package com.pahomovichk.pokedex.core.utils.net

import com.pahomovichk.pokedex.core.utils.exception.AppException
import com.pahomovichk.pokedex.core.utils.net.result.ErrorResponse
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

internal class ResultCall<T>(
    proxy: Call<T>,
    private val json: Json
) : CallDelegate<T, ResultResource<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ResultResource<T>>) {
        proxy.enqueue(ResultCallback(this, callback, json))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone(), json)
    }

    @Suppress("UNCHECKED_CAST")
    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<ResultResource<T>>,
        private val json: Json
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: ResultResource<T> = if (response.isSuccessful) {
                ResultResource.Success.HttpResponse(
                    value = response.body() as T,
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString()
                )
            } else {
                val errorResponse = json.decodeFromString<ErrorResponse>(
                    response.errorBody()?.string().toString()
                )

                ResultResource.Failure.Error(
                    AppException(
                        errorResponse.errorId,
                        errorResponse.applicationName,
                        errorResponse.message
                    )
                )
                ResultResource.Failure.Error(Exception())
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = ResultResource.Failure.Error(
                AppException(message = error.toString())
            )

            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}