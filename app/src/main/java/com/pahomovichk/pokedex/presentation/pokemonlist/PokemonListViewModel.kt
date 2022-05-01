package com.pahomovichk.pokedex.presentation.pokemonlist

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
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

    fun onLaunch() {
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
                        if (pokemonId.toInt() <= 10) {
                            println("POKEMON IS $entry")
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