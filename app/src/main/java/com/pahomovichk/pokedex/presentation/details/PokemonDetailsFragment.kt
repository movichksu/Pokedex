package com.pahomovichk.pokedex.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.gone
import com.pahomovichk.pokedex.core.utils.extensions.tryToGetInt
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.core.utils.extensions.visible
import com.pahomovichk.pokedex.core.utils.getPokemonArtwork
import com.pahomovichk.pokedex.data.network.dto.Pokemon
import com.pahomovichk.pokedex.data.network.dto.Type
import com.pahomovichk.pokedex.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details) {

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
                addTabs(
                    getString(R.string.tab_about),
                    getString(R.string.tab_base_stats),
                    getString(R.string.tab_evolutions)
                ) { tabIndex ->
                    viewModel.onTabSelected(tabIndex)
                    onTabSelected()
                }
                applyColorScheme(dominantColor)
                getTabAt(viewModel.currentTabIndex)?.select()
            }
        }
    }

    override fun initVM() {
        super.initVM()
        with(viewModel) {
            pokemonDetailsLiveData.observe(viewLifecycleOwner) { pokemon ->
                binding.pokemonNameText.text = pokemon.name
                Glide
                    .with(requireContext())
                    .load(getPokemonArtwork(pokemon.id.toString()))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(binding.pokemonImage)
                setPokemonTypes(pokemon.types)
                setAboutTabContent(pokemon)
                setStatsTabContent(pokemon)
                setEvolutionsTabContent(pokemon)
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    private fun setAboutTabContent(pokemon: Pokemon) {
        with(binding.info.aboutTabContent) {
            heightValue.text = getString(R.string.about_weight_value, pokemon.weight)
            weightValue.text = pokemon.weight.toString()
            speciesValue.text = pokemon.species.name
            var abilities = ""
            if (pokemon.abilities.isNotEmpty()) {
                pokemon.abilities.forEach {
                    abilities += "${it.ability.name}, "
                }
                abilitiesValue.text = abilities.dropLast(2)
            }
        }
    }

    private fun setStatsTabContent(pokemon: Pokemon) {
        with(binding.info.statsTabContent) {
            hpValue.text = pokemon.stats[0].base_stat.toString()
            attackValue.text = pokemon.stats[1].base_stat.toString()
            defenseValue.text = pokemon.stats[2].base_stat.toString()
            spAttackValue.text = pokemon.stats[3].base_stat.toString()
            spDefenseValue.text = pokemon.stats[4].base_stat.toString()
            speedValue.text = pokemon.stats[5].base_stat.toString()
            var totalStatsValue = 0
            pokemon.stats.forEach { totalStatsValue += it.base_stat }
            totalValue.text = totalStatsValue.toString()
        }
    }

    private fun setEvolutionsTabContent(pokemon: Pokemon) {
        with(binding.info.evolutionTabContent) {

        }
    }

    private fun setPokemonTypes(types : List<Type>){
        with(binding){
            if (types.isNotEmpty()) {
                pokemonTypeFirstText.apply {
                    text = types[0].type.name
                    visible()
                }
                if (types.size > 1) {
                    pokemonTypeSecondText.apply {
                        text = types[1].type.name
                        visible()
                    }
                }
            }
        }
    }

    private fun onTabSelected() {
        with(binding.info) {
            when (viewModel.currentTabIndex) {
                ABOUT_TAB_INDEX -> {
                    aboutTabContent.root.visible()
                    statsTabContent.root.gone()
                    evolutionTabContent.root.gone()
                }
                STATS_TAB_INDEX -> {
                    aboutTabContent.root.gone()
                    statsTabContent.root.visible()
                    evolutionTabContent.root.gone()
                }
                EVOLUTIONS_TAB_INDEX -> {
                    aboutTabContent.root.gone()
                    statsTabContent.root.gone()
                    evolutionTabContent.root.visible()
                }
                else -> {}
            }
        }
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