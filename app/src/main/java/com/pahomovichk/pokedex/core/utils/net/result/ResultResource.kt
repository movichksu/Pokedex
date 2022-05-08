package com.pahomovichk.pokedex.core.utils.net.result

import java.lang.Exception
import com.pahomovichk.pokedex.core.utils.net.result.HttpResponse as Response

sealed class ResultResource<out T> {

    sealed class Success<T> : ResultResource<T>() {
        abstract val value: T
        override fun toString() = "Success($value)"

        class Value<T>(override val value: T) : Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null
        ) : Success<T>(), Response
    }

    sealed class Failure(val error: Exception) : ResultResource<Nothing>() {

        override fun toString() = "Failure($error)"

        class Error(error: Exception) : Failure(error)
    }

    object InProgress : ResultResource<Nothing>()
}