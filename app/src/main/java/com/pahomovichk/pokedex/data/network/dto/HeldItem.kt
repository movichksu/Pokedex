package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)