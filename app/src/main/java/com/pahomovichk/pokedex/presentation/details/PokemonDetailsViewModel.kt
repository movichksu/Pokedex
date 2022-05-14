package com.pahomovichk.pokedex.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.core.utils.extensions.finally
import com.pahomovichk.pokedex.core.utils.extensions.onFailure
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.domain.interactor.PokemonNetworkInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel@Inject constructor(
    private val router: Router,
    private val interactor: PokemonNetworkInteractor
): ViewModel() {

    val pokemonDetailsLiveData = MutableLiveData<ResultResource<Pokemon>>()

    @PokemonInfoTabIndex
    var currentTabIndex = ABOUT_TAB_INDEX

    fun onTabSelected(@PokemonInfoTabIndex selectedTabIndex: Int) {
        currentTabIndex = selectedTabIndex
    }

    fun getPokemonData(id: Int) {
        pokemonDetailsLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            interactor.getPokemonInfo(id)
                .onFailure { Timber.e(it) }
                .finally { pokemonDetailsLiveData.postValue(it) }
        }
    }

    fun onBackPressed() {
        router.exit()
    }

}