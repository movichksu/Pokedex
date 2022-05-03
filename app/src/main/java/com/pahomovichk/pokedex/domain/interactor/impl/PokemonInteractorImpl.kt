package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.core.utils.ResultResponse
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonInteractorImpl @Inject constructor(
    private val pokeApi: PokeApi
): PokemonInteractor {

    override suspend fun getPokemonList(): ResultResponse<PokemonList> {
        val response = try {
            pokeApi.getPokemonList()
        } catch(e: Exception) {
            return ResultResponse.Error("An unknown error occured.")
        }
        return ResultResponse.Success(response)
    }

    override suspend fun getPokemonInfo(id: Int): ResultResponse<Pokemon> {
        val response = try {
            pokeApi.getPokemonInfo(id)
        } catch(e: Exception) {
            return ResultResponse.Error("An unknown error occured.")
        }
        return ResultResponse.Success(response)
    }
}