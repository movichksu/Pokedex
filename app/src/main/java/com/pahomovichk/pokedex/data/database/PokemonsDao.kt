package com.pahomovichk.pokedex.data.database

import androidx.room.*
import com.pahomovichk.pokedex.core.utils.Constants.POKEMONS_TABLE_NAME
import com.pahomovichk.pokedex.data.database.model.PokemonEntity

@Dao
interface PokemonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: PokemonEntity)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("DELETE FROM $POKEMONS_TABLE_NAME")
    suspend fun removeAll()

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME WHERE id = :itemId")
    fun getPokemon(itemId: Int): PokemonEntity
}