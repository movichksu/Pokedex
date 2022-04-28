package com.pahomovichk.pokedex.presentation.pokemonlist

import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentPokemonListBinding

class PokemonListFragment: BaseFragment(R.layout.fragment_pokemon_list) {

    private val binding by viewBinding { FragmentPokemonListBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun initUI() {
        // TODO
    }

    override fun onBackPressed() {
        // TODO
    }
}