package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class GenerationVii(
    val icons: Icons,
    @SerialName("ultra-sun-ultra-moon")
    val ultra_sun_ultra_moon: UltraSunUltraMoon
)