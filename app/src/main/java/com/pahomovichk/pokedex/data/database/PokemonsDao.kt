package com.pahomovichk.pokedex.data.database

import androidx.room.*
import com.pahomovichk.pokedex.core.utils.Constants.POKEMONS_TABLE_NAME
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: PokemonEntity)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME")
    fun getPokemons(): Flow<List<PokemonEntity>>

    @Query("DELETE FROM $POKEMONS_TABLE_NAME")
    suspend fun removeAll()

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME WHERE id = :itemId")
    fun getPokemon(itemId: Int): PokemonEntity

    @Query(
        """
        UPDATE pokedex 
        SET    is_favourite = 1 
        WHERE  id = :id 
    """
    )
    fun setPathologyFavouriteTrue(id: Int)

    @Query(
        """
        UPDATE pokedex
        SET    is_favourite = 0
        WHERE  id = :id 
    """
    )
    fun setPathologyFavouriteFalse(id: Int)
}