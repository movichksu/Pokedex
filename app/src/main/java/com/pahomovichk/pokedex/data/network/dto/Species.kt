package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Species(
    val capture_rate: Int,
    val color: Color
)