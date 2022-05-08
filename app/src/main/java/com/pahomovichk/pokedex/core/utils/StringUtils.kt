package com.pahomovichk.pokedex.core.utils

private const val BASE_IMAGE_CONTENT_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"

const val EMPTY_STRING = ""

fun getPokemonImage(id: String) = "$BASE_IMAGE_CONTENT_URL/${id}.png"

fun getPokemonArtwork(id: String) = "$BASE_IMAGE_CONTENT_URL/other/dream-world/${id}.svg"

fun getPokemonLargePngImage(id: String) = "$BASE_IMAGE_CONTENT_URL/other/official-artwork/${id}.png"