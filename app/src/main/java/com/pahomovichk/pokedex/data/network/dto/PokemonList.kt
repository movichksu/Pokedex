package com.pahomovichk.pokedex.data.network.dto

import java.io.Serializable

data class PokemonList(
    val results: List<Result>
): Serializable