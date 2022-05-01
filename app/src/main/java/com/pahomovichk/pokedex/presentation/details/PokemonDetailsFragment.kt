package com.pahomovichk.pokedex.presentation.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.tryToGetInt
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentPokemonDetailsBinding
import com.pahomovichk.pokedex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class PokemonDetailsFragment: BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding { FragmentPokemonDetailsBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonDetailsViewModel>()

    private val dominantColor: Int by lazy { tryToGetInt(DOMINANT_COLOR_KEY) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonData(tryToGetInt(POKEMON_ID_KEY))
    }

    override fun initUI() {
        with(binding) {
            root.setBackgroundColor(dominantColor)
            toolbar.setNavigationOnClickListener { onBackPressed() }
            info.tabLayout.apply {
                setSelectedTabIndicatorColor(dominantColor)
                // TODO implement Checking theme
                //setTabTextColors(Color.BLACK, dominantColor)
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    companion object {

        private const val POKEMON_ID_KEY = "POKEMON_ID_KEY"
        private const val DOMINANT_COLOR_KEY = "DOMINANT_COLOR_KEY"

        fun newInstance(id: Int, color: Int) = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                POKEMON_ID_KEY to id,
                DOMINANT_COLOR_KEY to color
            )
        }
    }
}