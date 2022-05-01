package com.pahomovichk.pokedex.data.network.dto

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)