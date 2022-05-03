package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Emerald(
    val front_default: String,
    val front_shiny: String
)