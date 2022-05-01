package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class GenerationI(
    @SerialName("red-blue")
    val red_blue: RedBlue,
    val yellow: Yellow
)