package com.pahomovichk.pokedex.core.utils.net

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultAdapterFactory(private val json: Json) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java) {
            if (returnType is ParameterizedType) {
                val callInnerType: Type = getParameterUpperBound(0, returnType)
                if (getRawType(callInnerType) == ResultResource::class.java) {
                    if (callInnerType is ParameterizedType) {
                        val resultInnerType = getParameterUpperBound(0, callInnerType)
                        return ResultCallAdapter<Any?>(resultInnerType, json)
                    }
                    return ResultCallAdapter<Nothing>(Nothing::class.java, json)
                }
            }
        }

        return null
    }
}

private class ResultCallAdapter<R>(private val type: Type, private val json: Json) :
    CallAdapter<R, Call<ResultResource<R>>> {

    override fun responseType() = type

    override fun adapt(call: Call<R>): Call<ResultResource<R>> = ResultCall(call, json)
}