package com.pahomovichk.pokedex.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.app.Screens
import com.pahomovichk.pokedex.core.ui.SingleLiveEvent
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.core.utils.getPokemonLargePngImage
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.model.PokemonItem
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val router: Router,
    private val dbInteractor: PokemonDbInteractor
) : ViewModel() {

    val pokemonListLiveData = MutableLiveData<ResultResource<List<PokemonItem>>>()
    val getPokemonEntityLiveEvent = SingleLiveEvent<ResultResource<PokemonEntity>>()
    val pokemonsList = mutableListOf<PokemonItem>()
    val filteredListLiveData = SingleLiveEvent<List<PokemonItem>>()

    fun onFirstLaunch() {
        getPokemonList()
    }

    fun onPokemonItemClicked(id: Int) {
        getPokemonEntityLiveEvent.value = ResultResource.InProgress
        viewModelScope.launch {
            dbInteractor.getPokemon(id)
                .onFailure { Timber.e(it) }
                .finally { getPokemonEntityLiveEvent.postValue(it) }
        }
    }

    fun onHeartClicked(id: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                dbInteractor.addToFavourites(id)
            } else {
                dbInteractor.removeFromFavourites(id)
            }
        }
    }

    fun onGotPokemonEntity(pokemonEntity: PokemonEntity) {
        router.navigateTo(Screens.PokemonDetails(pokemonEntity))
    }

    fun onBackPressed() {
        router.exit()
    }

    fun setBaseList(list: List<PokemonItem>) {
        pokemonsList.clear()
        pokemonsList.addAll(list)
    }

    fun onSearchPokemon(str: String) {
        filteredListLiveData.value =
            pokemonsList.filter {
                it.pokemonName.contains(str, true)
            }
    }

    private fun getPokemonList() {
        pokemonListLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            dbInteractor.getPokemons()
                .onSuccess { result ->
                    result.collect { pokemonsList ->
                        pokemonListLiveData.postValue(
                            ResultResource.Success.Value(
                                pokemonsList
                                    .map { entry ->
                                        PokemonItem(
                                            entry.id,
                                            entry.name,
                                            entry.is_favourite,
                                            entry.dominant_color,
                                            entry.types,
                                            getPokemonLargePngImage(entry.id.toString())
                                        )
                                    }
                            )
                        ).toSuccessResult()
                    }
                }
                .onFailure {
                    Timber.e(it)
                    pokemonListLiveData.postValue(it.toFailureResult())
                }
        }
    }
}