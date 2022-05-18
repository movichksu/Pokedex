package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChain(
    val chain: Chain,
    val id: Int
)