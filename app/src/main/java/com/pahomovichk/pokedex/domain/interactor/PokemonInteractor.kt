package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.ResultResponse
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList


interface PokemonInteractor {

    suspend fun getPokemonList(): ResultResponse<PokemonList>

    suspend fun getPokemonInfo(id: Int): ResultResponse<Pokemon>
}