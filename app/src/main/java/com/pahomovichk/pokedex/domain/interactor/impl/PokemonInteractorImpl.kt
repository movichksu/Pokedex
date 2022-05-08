package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class PokemonInteractorImpl @Inject constructor(
    private val pokeApi: PokeApi,
    private val dispatchers: DispatcherProvider
): PokemonInteractor {

    override suspend fun getPokemonList(): ResultResource<PokemonList> =
        withContext(dispatchers.io) {
            pokeApi.getPokemonList()
        }

    override suspend fun getPokemonInfo(id: Int): ResultResource<Pokemon> =
        withContext(dispatchers.io) {
            pokeApi.getPokemonInfo(id)
        }
}