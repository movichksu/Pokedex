package com.pahomovichk.pokedex.presentation.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.app.Screens
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.core.utils.extensions.finally
import com.pahomovichk.pokedex.core.utils.extensions.map
import com.pahomovichk.pokedex.core.utils.extensions.onFailure
import com.pahomovichk.pokedex.core.utils.getPokemonImage
import com.pahomovichk.pokedex.data.model.PokemonItemEtity
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val router: Router,
    private val interactor: PokemonInteractor
): ViewModel() {

    val pokemonListLiveData = MutableLiveData<ResultResource<List<PokemonItemEtity>>>()

    fun onFirstLaunch() {
        getPokemonList()
    }

    fun onPokemonItemClicked(id: Int, color: Int) {
        router.navigateTo(Screens.PokemonDetails(id, color))
    }

    fun onBackPressed() {
        router.exit()
    }

    private fun getPokemonList() {
        pokemonListLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            interactor.getPokemonList()
                .map { pokemons ->
                    pokemons.results.mapIndexed { index, entry ->
                        val pokemonId = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        PokemonItemEtity(
                            pokemonId.toInt(),
                            entry.name,
                            getPokemonImage(pokemonId)
                        )
                    }
                }
                .onFailure { Timber.e(it) }
                .finally { pokemonListLiveData.postValue(it) }
        }
    }
}