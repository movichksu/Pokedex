package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenerationIi(
    val crystal: Crystal,
    val gold: Gold,
    val silver: Silver
)