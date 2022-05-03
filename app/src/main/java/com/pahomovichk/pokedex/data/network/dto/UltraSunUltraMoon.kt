package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UltraSunUltraMoon(
    val front_default: String,
    val front_female: String,
    val front_shiny: String,
    val front_shiny_female: String
)