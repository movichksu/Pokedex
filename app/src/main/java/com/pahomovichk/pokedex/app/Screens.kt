package com.pahomovichk.pokedex.app

import com.pahomovichk.pokedex.presentation.details.PokemonDetailsFragment
import com.pahomovichk.pokedex.presentation.pokemonlist.PokemonListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object PokemonList : SupportAppScreen() {

        override fun getFragment() = PokemonListFragment()
    }

    object PokemonDetails : SupportAppScreen() {

        override fun getFragment() = PokemonDetailsFragment()
    }
}