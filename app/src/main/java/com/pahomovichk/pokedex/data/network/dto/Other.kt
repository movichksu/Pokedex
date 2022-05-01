package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerialName("official-artwork")
    val official_artwork: OfficialArtwork
)