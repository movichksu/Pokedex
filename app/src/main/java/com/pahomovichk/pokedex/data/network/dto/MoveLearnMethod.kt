package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoveLearnMethod(
    val name: String,
    val url: String
)