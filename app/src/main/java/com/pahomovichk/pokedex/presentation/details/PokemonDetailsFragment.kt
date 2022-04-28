package com.pahomovichk.pokedex.presentation.details

import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentPokemonListBinding

class PokemonDetailsFragment: BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding { FragmentPokemonListBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonDetailsViewModel>()

    override fun initUI() {
        // TODO
    }

    override fun onBackPressed() {
        // TODO
    }
}