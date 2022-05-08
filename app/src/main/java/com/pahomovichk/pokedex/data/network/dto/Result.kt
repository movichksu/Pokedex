package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val name: String,
    val url: String,
    val id: Int,
    val height: Int,
    val weight: Int
)