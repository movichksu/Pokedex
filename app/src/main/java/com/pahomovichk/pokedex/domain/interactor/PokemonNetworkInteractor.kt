package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.net.result.EmptyResult
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList


interface PokemonNetworkInteractor {

    suspend fun getPokemonList(): ResultResource<PokemonList>

    suspend fun getPokemonInfo(id: Int): ResultResource<Pokemon>

    suspend fun getRemotePokemons() : ResultResource<List<Pokemon>>
}