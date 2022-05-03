package com.pahomovichk.pokedex.data.network.api

import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = Constants.POKEMONS_LIMIT
    ): PokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ) : Pokemon
}