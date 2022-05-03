package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)