package com.pahomovichk.pokedex.data.database.mapper

import android.annotation.SuppressLint
import com.pahomovichk.pokedex.core.utils.Constants.BASE_PERAMETERS_DIVIDER
import com.pahomovichk.pokedex.core.utils.extensions.capitalizeFirst
import com.pahomovichk.pokedex.data.database.model.BaseStat
import com.pahomovichk.pokedex.data.database.model.DominantColor
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.AbilityX
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.TypeX

fun List<Pokemon>.mapToPokemonEntity(): List<PokemonEntity> {
    val pokemonList = arrayListOf<PokemonEntity>()
    forEach {
        pokemonList.add(it.toPokemonEntity())
    }
    return pokemonList
}

@SuppressLint("DefaultLocale")
fun Pokemon.toPokemonEntity(): PokemonEntity =
    PokemonEntity(
        this.id,
        this.name.capitalizeFirst(),
        " ",
        this.height/BASE_PERAMETERS_DIVIDER,
        this.weight/BASE_PERAMETERS_DIVIDER,
        getDominantColor(this.color ?: "gray"),
        false,
        this.getAbilities(),
        this.getTypes(),
        this.getStats(),
        " ",
    )

fun Pokemon.getAbilities() =
    this.abilities
        .map { AbilityX(it.ability.name.capitalizeFirst()) }

fun Pokemon.getTypes() =
    this.types
        .map { TypeX(it.type.name.capitalizeFirst()) }

fun Pokemon.getStats() = this.stats.map { BaseStat(it.base_stat) }

@SuppressLint("DefaultLocale")
fun getDominantColor(color: String) : DominantColor = enumValueOf(color.toUpperCase())