package com.pahomovichk.pokedex.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    )

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    )
}