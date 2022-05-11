package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val types: List<Type>,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val stats: List<Stat>,
    val forms: List<Form>,
    val is_default: Boolean,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
)