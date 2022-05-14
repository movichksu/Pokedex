package com.pahomovichk.pokedex.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.ui.coloring.parseToColor
import com.pahomovichk.pokedex.core.utils.EMPTY_STRING
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.core.utils.getPokemonLargePngImage
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.network.dto.TypeX
import com.pahomovichk.pokedex.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding { FragmentPokemonDetailsBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonDetailsViewModel>()

    private val pokemon by lazy { tryToGetSerializable<PokemonEntity>(POKEMON_KEY) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUI() {
        with(binding) {
            root.setBackgroundColor(getColor(pokemon.dominant_color.parseToColor()))
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
                applyColorScheme(getColor(pokemon.dominant_color.parseToColor()))
                getTabAt(viewModel.currentTabIndex)?.select()
            }
            pokemonNameText.text = pokemon.name
            pokemonIdText.text = getString(R.string.pokemon_number_format, pokemon.id)

            Glide
                .with(requireContext())
                .load(getPokemonLargePngImage(pokemon.id.toString()))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(pokemonImage)

            setPokemonTypes(pokemon.types)
            setAboutTabContent(pokemon)
            setStatsTabContent(pokemon)
            setEvolutionsTabContent(pokemon)
        }
    }

    override fun initVM() {
        super.initVM()
        with(viewModel) {

        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    private fun setAboutTabContent(pokemon: PokemonEntity) {
        with(binding.info.aboutTabContent) {
            heightValue.text = getString(R.string.about_height_value, pokemon.height)
            weightValue.text = getString(R.string.about_weight_value, pokemon.weight)
            var abilities = EMPTY_STRING
            if (pokemon.abilities.isNotEmpty()) {
                pokemon.abilities.forEach {
                    abilities += "${it.name}, "
                }
                abilitiesValue.text = abilities.dropLast(2)
            }
        }
    }

    private fun setStatsTabContent(pokemon: PokemonEntity) {
        with(binding.info.statsTabContent) {
            hpValue.text = pokemon.stats[0].stat.toString()
            attackValue.text = pokemon.stats[1].stat.toString()
            defenseValue.text = pokemon.stats[2].stat.toString()
            spAttackValue.text = pokemon.stats[3].stat.toString()
            spDefenseValue.text = pokemon.stats[4].stat.toString()
            speedValue.text = pokemon.stats[5].stat.toString()
            var totalStatsValue = 0
            pokemon.stats.forEach { totalStatsValue += it.stat }
            totalValue.text = totalStatsValue.toString()
        }
    }

    private fun setEvolutionsTabContent(pokemon: PokemonEntity) {
        with(binding.info.evolutionTabContent) {

        }
    }

    private fun setPokemonTypes(types : List<TypeX>){
        with(binding){
            if (types.isNotEmpty()) {
                pokemonTypeFirstText.apply {
                    text = types[0].name
                    visible()
                }
                if (types.size > 1) {
                    pokemonTypeSecondText.apply {
                        text = types[1].name
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

        private const val POKEMON_KEY = "POKEMON_KEY"

        fun newInstance(pokemon: PokemonEntity) = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                POKEMON_KEY to pokemon
            )
        }
    }
}