package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.Pokemon

interface PokemonDbInteractor {

    suspend fun writePokemonsDao(list: List<Pokemon>) : ResultResource<Boolean>

    suspend fun getPokemosList() : ResultResource<List<PokemonEntity>>

    suspend fun getPokemon(id: Int) : ResultResource<PokemonEntity>
}