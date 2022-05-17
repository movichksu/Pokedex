package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.database.PokemonsDao
import com.pahomovichk.pokedex.data.database.mapper.mapToPokemonEntity
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonDbInteractorImpl @Inject constructor(
    private val dao: PokemonsDao,
    private val dispatchers: DispatcherProvider
): PokemonDbInteractor {

    override suspend fun writePokemonsDao(list: List<Pokemon>): ResultResource<Boolean> =
        withContext(dispatchers.io) {
            try {
                if (list.isNotEmpty()) {
                    val mappedPokemons = list.mapToPokemonEntity()
                    dao.insert(mappedPokemons)
                }
            } catch (e: Exception) {
                return@withContext ResultResource.Failure.Error(e)
            }
            return@withContext ResultResource.Success.Value(true)
        }

    override suspend fun getPokemosList(): ResultResource<List<PokemonEntity>> =
        withContext(dispatchers.io) {
            val pokemons = arrayListOf<PokemonEntity>()
            try {
                pokemons.addAll(dao.getAllPokemons())
            } catch (e: Exception) {
                return@withContext ResultResource.Failure.Error(e)
            }
            return@withContext ResultResource.Success.Value(pokemons)
        }

    override suspend fun getPokemon(id: Int): ResultResource<PokemonEntity> =
        withContext(dispatchers.io) {
            try {
                val pokemon = dao.getPokemon(id)
                return@withContext ResultResource.Success.Value(pokemon)
            } catch (e: Exception) {
                return@withContext ResultResource.Failure.Error(e)
            }
        }
}