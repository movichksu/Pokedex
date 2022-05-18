package com.pahomovichk.pokedex.presentation.collection

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
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val router: Router,
    private val interactor: PokemonDbInteractor
) : ViewModel() {

    val pokemonListLiveData = MutableLiveData<ResultResource<List<PokemonItem>>>()
    val getPokemonEntityLiveEvent = SingleLiveEvent<ResultResource<PokemonEntity>>()

    fun onFirstLaunch() {
        getPokemonList()
    }

    fun onGotPokemonEntity(pokemonEntity: PokemonEntity) {
        router.navigateTo(Screens.PokemonDetails(pokemonEntity))
    }

    fun onHeartClicked(id: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                interactor.addToFavourites(id)
            } else {
                interactor.removeFromFavourites(id)
            }
        }
    }

    fun onPokemonItemClicked(id: Int) {
        getPokemonEntityLiveEvent.value = ResultResource.InProgress
        viewModelScope.launch {
            interactor.getPokemon(id)
                .onFailure { Timber.e(it) }
                .finally { getPokemonEntityLiveEvent.postValue(it) }
        }
    }

    private fun getPokemonList() {
        pokemonListLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            interactor.getPokemons()
                .onSuccess { result ->
                    result.collect { pokemonsList ->
                        pokemonListLiveData.postValue(
                            ResultResource.Success.Value(
                                pokemonsList
                                    .filter { it.is_favourite }
                                    .map { entry ->
                                        PokemonItem(
                                            entry.id,
                                            entry.name,
                                            entry.is_favourite,
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