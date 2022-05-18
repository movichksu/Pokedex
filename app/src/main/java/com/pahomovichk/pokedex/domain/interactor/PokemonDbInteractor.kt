package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface PokemonDbInteractor {

    suspend fun writePokemonsDao(list: List<Pokemon>) : ResultResource<Boolean>

    suspend fun getPokemosList() : ResultResource<List<PokemonEntity>>

    suspend fun getPokemons() : ResultResource<Flow<List<PokemonEntity>>>

    suspend fun getPokemon(id: Int) : ResultResource<PokemonEntity>

    suspend fun addToFavourites(id: Int)

    suspend fun removeFromFavourites(id: Int)
}