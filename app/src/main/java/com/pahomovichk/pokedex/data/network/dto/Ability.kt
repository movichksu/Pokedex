package com.pahomovichk.pokedex.data.network.dto

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)