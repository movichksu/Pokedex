package com.pahomovichk.pokedex.presentation.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel@Inject constructor(
    private val router: Router,
): ViewModel() {

    fun getPokemonData(id: Int) {

    }

    fun onBackPressed() {
        router.exit()
    }

}