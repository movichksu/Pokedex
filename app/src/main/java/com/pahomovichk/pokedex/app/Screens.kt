package com.pahomovichk.pokedex.app

import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.presentation.details.PokemonDetailsFragment
import com.pahomovichk.pokedex.presentation.pokemonlist.PokemonListFragment
import com.pahomovichk.pokedex.presentation.showmodel.PokemonModelFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object PokemonList : SupportAppScreen() {

        override fun getFragment() = PokemonListFragment()
    }

    data class PokemonDetails(
        private val pokemonEntity: PokemonEntity
    ) : SupportAppScreen() {

        override fun getFragment() =
            PokemonDetailsFragment.newInstance(pokemonEntity)
    }

    data class PokemonModel(
        private val pokemonEntity: PokemonEntity
    ) : SupportAppScreen() {

        override fun getFragment() =
            PokemonModelFragment.newInstance(pokemonEntity)
    }
}