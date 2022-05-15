package com.pahomovichk.pokedex.data.database

import androidx.room.TypeConverter
import com.pahomovichk.pokedex.core.utils.EMPTY_STRING
import com.pahomovichk.pokedex.data.database.model.BaseStat
import com.pahomovichk.pokedex.data.database.model.DominantColor
import com.pahomovichk.pokedex.data.network.dto.*

class Converters {

    @TypeConverter
    fun fromTypeX(types: List<TypeX>): String {
        val typesString = StringBuilder(EMPTY_STRING)
        if (types.isNotEmpty()) {
            types.forEach {
                typesString.append("${it.name},")
            }
        }
        return typesString.dropLast(1).toString()
    }

    @TypeConverter
    fun toTypeX(types: String): List<TypeX> {
        return types
            .split(",")
            .map { type -> TypeX(type) }
    }

    @TypeConverter
    fun fromAbilityX(abilities: List<AbilityX>): String {
        val abilitiesString = StringBuilder(EMPTY_STRING)
        if (abilities.isNotEmpty()) {
            abilities.forEach {
                abilitiesString.append("${it.name},")
            }
        }
        return abilitiesString.dropLast(1).toString()
    }

    @TypeConverter
    fun toAbilityX(abilities: String): List<AbilityX> =
        abilities
            .split(",")
            .map { ability -> AbilityX(ability) }

    @TypeConverter
    fun fromBaseStat(stats: List<BaseStat>): String {
        val statsString = StringBuilder(EMPTY_STRING)
        if (stats.isNotEmpty()) {
            stats.forEach {
                statsString.append("${it.stat},")
            }
        }
        return statsString.dropLast(1).toString()
    }

    @TypeConverter
    fun toBaseStat(stats: String): List<BaseStat> =
        stats
            .split(",")
            .map { stat -> BaseStat(stat.toInt() ?: 0) }

    @TypeConverter
    fun fromPathologyStatus(color: DominantColor) = color.name

    @TypeConverter
    fun toPathologyStatus(color: String): DominantColor = enumValueOf(color)
}