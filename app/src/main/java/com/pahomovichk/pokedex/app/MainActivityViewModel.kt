package com.pahomovichk.pokedex.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.core.ui.SingleLiveEvent
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import com.pahomovichk.pokedex.domain.interactor.PokemonNetworkInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val router: Router,
    private val networInteractor: PokemonNetworkInteractor,
    private val dbInteractor: PokemonDbInteractor
    ): ViewModel() {

    val getRemotePokemonsLiveData = SingleLiveEvent<ResultResource<List<Pokemon>>>()
    val writePokemonsDaoLiveEvent = SingleLiveEvent<ResultResource<Boolean>>()

    fun onFirstLaunch() {
        getRemotePokemonsList()
    }

    private fun getRemotePokemonsList() {
        getRemotePokemonsLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            networInteractor.getRemotePokemons()
                .onFailure { Timber.e(it) }
                .finally { getRemotePokemonsLiveData.postValue(it) }
        }
    }

    fun writePokemonsToDatabase(list: List<Pokemon>) {
        writePokemonsDaoLiveEvent.value = ResultResource.InProgress
        viewModelScope.launch {
            dbInteractor.writePokemonsDao(list)
                .onFailure { Timber.e(it) }
                .finally { writePokemonsDaoLiveEvent.postValue(it) }
        }
    }

    fun onDatabaseUpdatedSuccessfully() {
        router.newRootScreen(Screens.PokemonList)
    }
}