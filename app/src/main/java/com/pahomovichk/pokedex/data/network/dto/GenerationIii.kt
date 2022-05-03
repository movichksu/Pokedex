package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIii(
    val emerald: Emerald,
    @SerialName("firered-leafgreen")
    val firered_leafgreen: FireredLeafgreen,
    @SerialName("ruby-sapphire")
    val ruby_sapphire: RubySapphire
)