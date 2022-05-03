package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class VersionDetail(
    val rarity: Int,
    val version: VersionX
)