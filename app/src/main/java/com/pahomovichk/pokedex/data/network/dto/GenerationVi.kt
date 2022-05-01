package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class GenerationVi(
    @SerialName("omegaruby-alphasapphire")
    val omegaruby_alphasapphire: OmegarubyAlphasapphire,
    @SerialName("x-y")
    val x_y: XY
)