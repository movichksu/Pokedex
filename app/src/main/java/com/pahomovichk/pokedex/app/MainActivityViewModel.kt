package com.pahomovichk.pokedex.app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val router: Router,
): ViewModel() {

    fun onLaunch() {
        router.newRootScreen(Screens.PokemonList)
    }
}