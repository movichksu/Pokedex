package com.pahomovichk.pokedex.data.model

import com.pahomovichk.pokedex.data.database.model.DominantColor
import com.pahomovichk.pokedex.data.network.dto.TypeX

data class PokemonItem(
    val id: Int,
    val pokemonName: String,
    val isFavourite: Boolean,
    val dominantColor: DominantColor,
    val types: List<TypeX>,
    val imageUrl: String
)