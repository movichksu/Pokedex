package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class IconsX(
    val front_default: String,
    val front_female: String
)