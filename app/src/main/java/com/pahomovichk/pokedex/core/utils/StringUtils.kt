package com.pahomovichk.pokedex.core.utils

const val EMPTY_STRING = ""
fun getPokemonImage(id: String) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"