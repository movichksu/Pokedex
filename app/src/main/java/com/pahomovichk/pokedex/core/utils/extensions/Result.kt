package com.pahomovichk.pokedex.core.utils.extensions

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import java.lang.Exception

fun <T> ResultResource<T>.isSuccess(): Boolean {
    return this is ResultResource.Success
}

fun <T> ResultResource<T>.asSuccess(): ResultResource.Success<T> {
    return this as ResultResource.Success<T>
}

fun <T> ResultResource<T>.asFailure(): ResultResource.Failure {
    return this as ResultResource.Failure
}

inline fun <T> ResultResource<T>.onSuccess(action: (value: T) -> Unit): ResultResource<T> {
    if (isSuccess()) {
        action(asSuccess().value)
    }
    return this
}

inline fun <T> ResultResource<T>.onProgress(action: () -> Unit): ResultResource<T> {
    if (this is ResultResource.InProgress) {
        action()
    }
    return this
}

inline fun <T> ResultResource<T>.finally(action: (ResultResource<T>) -> Unit): ResultResource<T> {
    action(this)
    return this
}

inline fun <T> ResultResource<T>.onFailure(
    action: (exception: Exception) -> Unit
): ResultResource<T> {
    if (this is ResultResource.Failure) {
        action((asFailure() as ResultResource.Failure.Error).error)
    }
    return this
}

fun <T, R> ResultResource<T>.map(transform: (value: T) -> R): ResultResource<R> {
    return when (this) {
        is ResultResource.Success -> ResultResource.Success.Value(transform(value))
        is ResultResource.Failure -> this
        is ResultResource.InProgress -> this
    }
}

fun <T> T.toSuccessResult(): ResultResource.Success<T> = ResultResource.Success.Value(this)

fun <T : Exception> T.toFailureResult(): ResultResource.Failure = ResultResource.Failure.Error(this)