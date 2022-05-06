package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList


interface PokemonInteractor {

    suspend fun getPokemonList(): ResultResource<PokemonList>

    suspend fun getPokemonInfo(id: Int): ResultResource<Pokemon>
}