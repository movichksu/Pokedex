package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Chain(
    val evolves_to: List<EvolvesTo>,
    val species: Result
)