package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlavorTextEntry(
    val flavor_text: String,
    val language : Language
)