package com.pahomovichk.pokedex.core.utils.exception

import com.pahomovichk.pokedex.core.utils.EMPTY_STRING
import java.lang.Exception

class AppException(
    val errorId: String = EMPTY_STRING,
    val applicationName: String = EMPTY_STRING,
    message: String = EMPTY_STRING
) : Exception(message) {

    override fun toString(): String {
        return "AppException(message=$message)"
    }
}