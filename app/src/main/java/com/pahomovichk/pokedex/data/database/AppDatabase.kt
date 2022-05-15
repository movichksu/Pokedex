package com.pahomovichk.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pahomovichk.pokedex.data.database.model.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonsDao(): PokemonsDao
}