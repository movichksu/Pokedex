package com.pahomovichk.pokedex.presentation.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.app.Screens
import com.pahomovichk.pokedex.core.utils.ResultResponse
import com.pahomovichk.pokedex.core.utils.getPokemonImage
import com.pahomovichk.pokedex.data.model.PokemonItemEtity
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val router: Router,
    private val interactor: PokemonInteractor
): ViewModel() {

    val pokemonListLiveData = MutableLiveData<List<PokemonItemEtity>>()

    fun onFirstLaunch() {
        getPokemonList()
    }

    fun onPokemonItemClicked(id: Int, color: Int) {
        router.navigateTo(Screens.PokemonDetails(id, color))
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            val result = interactor.getPokemonList()
            when(result) {
                is ResultResponse.Loading -> {
                    // TODO show loader
                }
                is ResultResponse.Success -> {
                    // TODO hide loader
                    val entities = result.data!!.results.mapIndexed { index, entry ->
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
                    pokemonListLiveData.value = entities
                }
                is ResultResponse.Error -> {
                    // TODO hide loader
                    // TODO show error message
                }
            }
        }
    }
}