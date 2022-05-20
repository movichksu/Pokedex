package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Species(
    var genera: List<Genera>,
    var flavor_text_entries: List<FlavorTextEntry>,
    val evolution_chain: EvolutionChainUrl,
    val capture_rate: Int,
    val color: Color
)