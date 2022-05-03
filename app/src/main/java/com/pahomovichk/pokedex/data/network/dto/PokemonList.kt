package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    val results: List<Result>
)