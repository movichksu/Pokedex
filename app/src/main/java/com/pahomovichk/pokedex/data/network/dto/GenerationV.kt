package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class GenerationV(
    @SerialName("black-white")
    val black_white: BlackWhite
)