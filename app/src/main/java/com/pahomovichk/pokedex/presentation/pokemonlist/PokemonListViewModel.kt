package com.pahomovichk.pokedex.presentation.pokemonlist

import androidx.lifecycle.ViewModel
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val router: Router,
    private val interactor: PokemonInteractor
): ViewModel() {

}