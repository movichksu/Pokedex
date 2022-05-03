package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Icons(
    val front_default: String,
    val front_female: Any
)