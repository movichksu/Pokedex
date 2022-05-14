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
import com.pahomovichk.pokedex.data.model.PokemonItem
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val router: Router,
    private val dbInteractor: PokemonDbInteractor
): ViewModel() {

    val pokemonListLiveData = MutableLiveData<ResultResource<List<PokemonItem>>>()

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
            dbInteractor.getPokemosList()
                .map { pokemons ->
                    pokemons.mapIndexed { index, entry ->
                        PokemonItem(
                            entry.id,
                            entry.name,
                            getPokemonImage(entry.id.toString())
                        )
                    }
                }
                .onFailure { Timber.e(it) }
                .finally { pokemonListLiveData.postValue(it) }
        }
    }
}