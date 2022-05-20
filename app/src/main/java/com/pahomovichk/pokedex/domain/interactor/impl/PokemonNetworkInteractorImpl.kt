package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.core.utils.extensions.asSuccess
import com.pahomovichk.pokedex.core.utils.extensions.getIdFromUrl
import com.pahomovichk.pokedex.core.utils.extensions.map
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.PokemonList
import com.pahomovichk.pokedex.data.network.dto.EvolutionChain
import com.pahomovichk.pokedex.domain.interactor.PokemonNetworkInteractor
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class PokemonNetworkInteractorImpl @Inject constructor(
    private val pokeApi: PokeApi,
    private val dispatchers: DispatcherProvider
): PokemonNetworkInteractor {

    override suspend fun getPokemonList(): ResultResource<PokemonList> =
        withContext(dispatchers.io) {
            pokeApi.getPokemonList()
        }

    override suspend fun getRemotePokemons() : ResultResource<List<Pokemon>> =
        withContext(dispatchers.io) {
            pokeApi.getPokemonList().map {
                val newList = mutableListOf<Pokemon>()
                it.results.forEach { pokemon ->
                    launch {
                        val poke = pokeApi.getPokemonInfo(pokemon.name).asSuccess().value
                        val spec = pokeApi.getSpecies(pokemon.name).asSuccess().value
                        newList.add(
                            poke.copy(
                                evolution_chain_id =
                                    spec.evolution_chain.url.getIdFromUrl().toInt(),
                                capture_rate = spec.capture_rate,
                                color = spec.color.name,
                                genera = spec.genera[7],
                                description =
                                    spec.flavor_text_entries.filter { it.language.name == "en" }
                            )
                        )
                    }
                }
                newList
            }
        }

    override suspend fun getEvolutionChain(chainId: Int): ResultResource<EvolutionChain> =
        withContext(dispatchers.io) {
            pokeApi.getEvolutionChain(chainId)
        }

    override suspend fun getPokemonInfo(id: Int): ResultResource<Pokemon> =
        withContext(dispatchers.io) {
            pokeApi.getPokemonInfo(id)
        }
}