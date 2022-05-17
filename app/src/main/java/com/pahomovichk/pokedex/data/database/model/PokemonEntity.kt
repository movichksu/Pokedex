package com.pahomovichk.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pahomovichk.pokedex.core.utils.Constants.POKEMONS_TABLE_NAME
import com.pahomovichk.pokedex.data.network.dto.AbilityX
import com.pahomovichk.pokedex.data.network.dto.TypeX
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = POKEMONS_TABLE_NAME)
data class PokemonEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "genera")
    val genera: String,
    @ColumnInfo(name = "height")
    val height: Float,
    @ColumnInfo(name = "weight")
    val weight: Float,
    @ColumnInfo(name = "dominant_color")
    val dominant_color: DominantColor,
    @ColumnInfo(name = "is_favourite")
    val is_favourite: Boolean,
    @ColumnInfo(name = "abilities")
    val abilities: List<AbilityX>,
    @ColumnInfo(name = "types")
    val types: List<TypeX>,
    @ColumnInfo(name = "stats")
    val stats: List<BaseStat>,
    @ColumnInfo(name = "description")
    val description: String
)