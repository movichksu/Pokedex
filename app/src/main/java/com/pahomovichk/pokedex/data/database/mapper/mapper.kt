package com.pahomovichk.pokedex.data.database.mapper

import android.graphics.Color
import com.pahomovichk.pokedex.core.ui.coloring.parseToColor
import com.pahomovichk.pokedex.data.database.model.BaseStat
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.Pokemon

fun List<Pokemon>.mapToPokemonEntity(): List<PokemonEntity> {
    val pokemonList = arrayListOf<PokemonEntity>()
    forEach {
        pokemonList.add(it.toPokemonEntity())
    }
    return pokemonList
}

fun Pokemon.toPokemonEntity(): PokemonEntity =
    PokemonEntity(
        this.id,
        this.name,
        " ",
        this.height,
        this.weight,
        this.color?.parseToColor() ?: Color.GRAY,
        false,
        this.getAbilities(),
        this.getTypes(),
        this.getStats(),
        " ",
    )

fun Pokemon.getAbilities() = this.abilities.map { it.ability }
fun Pokemon.getTypes() = this.types.map { it.type }
fun Pokemon.getStats() = this.stats.map { BaseStat(it.base_stat) }