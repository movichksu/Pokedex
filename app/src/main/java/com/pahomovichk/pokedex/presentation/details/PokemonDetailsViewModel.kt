package com.pahomovichk.pokedex.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.core.utils.ResultResponse
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel@Inject constructor(
    private val router: Router,
    private val interactor: PokemonInteractor
): ViewModel() {

    val pokemonDetailsLiveData = MutableLiveData<Pokemon>()

    @PokemonInfoTabIndex
    var currentTabIndex = ABOUT_TAB_INDEX

    fun onTabSelected(@PokemonInfoTabIndex selectedTabIndex: Int) {
        currentTabIndex = selectedTabIndex
    }

    fun getPokemonData(id: Int) {
        println("POKEMON GET DATA")
        viewModelScope.launch {
            val result = interactor.getPokemonInfo(id)
            when(result) {
                is ResultResponse.Loading -> {
                    // TODO show loader
                }
                is ResultResponse.Success -> {
                    // TODO hide loader
                    val entity = result.data!!
                    pokemonDetailsLiveData.value = entity
                }
                is ResultResponse.Error -> {
                    // TODO hide loader
                    // TODO show error message
                }
            }
        }
    }

    fun onBackPressed() {
        router.exit()
    }

}