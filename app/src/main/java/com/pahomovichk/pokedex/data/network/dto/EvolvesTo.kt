package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EvolvesTo(
    val evolves_to: List<EvolvesTo>,
    val is_baby: Boolean,
    val species: Result
)