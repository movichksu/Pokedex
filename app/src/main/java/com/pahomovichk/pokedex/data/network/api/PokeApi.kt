package com.pahomovichk.pokedex.data.network.api

import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList
import com.pahomovichk.pokedex.data.network.dto.Species
import com.pahomovichk.pokedex.data.network.dto.EvolutionChain
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = Constants.POKEMONS_LIMIT
    ): ResultResource<PokemonList>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ) : ResultResource<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ) : ResultResource<Pokemon>

    @GET("pokemon-species/{name}")
    suspend fun getSpecies(
        @Path("name") name: String
    ): ResultResource<Species>

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(
        @Path("id") id: Int
    ): ResultResource<EvolutionChain>
}