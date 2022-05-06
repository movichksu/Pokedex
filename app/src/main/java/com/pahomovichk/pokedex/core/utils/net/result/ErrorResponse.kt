package com.pahomovichk.pokedex.core.utils.net.result

import com.pahomovichk.pokedex.core.utils.EMPTY_STRING
import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val errorId: String = "0",
    val message: String = EMPTY_STRING,
    val applicationName: String = "Pokedex",
)