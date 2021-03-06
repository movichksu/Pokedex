package com.pahomovichk.pokedex.presentation.details

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import android.view.*
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
import com.github.mikephil.charting.formatter.ValueFormatter
import com.pahomovichk.pokedex.presentation.details.adapter.EvolutionsChainAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_details_evolution.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details),
    PopupMenu.OnMenuItemClickListener {

    private val binding by viewBinding { FragmentPokemonDetailsBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonDetailsViewModel>()

    private val pokemon: PokemonEntity
            by lazy { Json.decodeFromString(tryToGetString(POKEMON_KEY)) }

    private val barChartList = arrayListOf<BarEntry>()

    private lateinit var evolutionsAdapter: EvolutionsChainAdapter

    private var isPokemonFavourite = false

    override fun onFirstLaunch() {
        super.onFirstLaunch()
        viewModel.getEvolutionChain(pokemon.evolution_chain_id)
    }

    override fun initUI() {
        with(binding) {
            isPokemonFavourite = pokemon.is_favourite
            root.setBackgroundColor(getColor(pokemon.dominant_color.parseToColor()))
            toolbar.setNavigationOnClickListener { onBackPressed() }
            toolbar.setOnEndIconClickListener {
                showMenu()
            }
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
            chainMutableLiveData.observe(viewLifecycleOwner) { result ->
                showProgress(false)
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { evolutionChain ->
                        evolutionsAdapter.setItems(evolutionChain)
                        if (evolutionChain.isNotEmpty()) {
                            evolutionsRecycler.visible()
                            doesNotEvoluteText.gone()
                        } else {
                            evolutionsRecycler.gone()
                            doesNotEvoluteText.visible()
                        }
                    }
                    .onFailure {
                        requireContext().toast(
                            getString(R.string.error_message_fail_to_get_evolution_chain)
                        )
                    }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            FAVOURITE_MENU_TAB_ID -> {
                if (item.title == getString(R.string.menu_item_remove_from_favourites)) {
                    isPokemonFavourite = false
                    viewModel.onHeartClicked(pokemon.id, isPokemonFavourite)
                } else {
                    isPokemonFavourite = true
                    viewModel.onHeartClicked(pokemon.id, isPokemonFavourite)
                }
                true
            }
            R.id.sharePokemon -> {
                //
                true
            }
            R.id.openPokemonModel -> {
                viewModel.onOpenModelClicked(pokemon)
                true
            }
            else -> false
        }
    }

    private fun showMenu() {
        PopupMenu(requireContext(), binding.toolbar.getEndIconView()).apply {
            setOnMenuItemClickListener(this@PokemonDetailsFragment)
            inflate(R.menu.pokemon_details_menu)
            val text = if (isPokemonFavourite) {
                R.string.menu_item_remove_from_favourites
            } else {
                R.string.menu_item_add_to_favourites
            }
            menu.add(R.id.pokemonDetailsMenu, FAVOURITE_MENU_TAB_ID, 0, text)
        }.show()
    }

    private fun setAboutTabContent(pokemon: PokemonEntity) {
        with(binding.info.aboutTabContent) {
            aboutText.text = pokemon.description
            speciesValue.text = pokemon.genera
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
            totalValue.text = getString(R.string.total_value, totalStatsValue)

            pokemon.stats
                .asReversed()
                .forEachIndexed { index, baseStat ->
                    barChartList.add(BarEntry(index.toFloat(), baseStat.stat.toFloat()))
                }
        }
    }

    private fun initializeChart(pokemon: PokemonEntity) {
        val barChart: HorizontalBarChart = binding.info.statsTabContent.horizontalBarChart
        val legendList = arrayListOf(
            getString(R.string.stat_hp),
            getString(R.string.stat_attack),
            getString(R.string.stat_defense),
            getString(R.string.stat_sp_atk),
            getString(R.string.stat_sp_def),
            getString(R.string.stat_speed)
        )

        barChart.apply {
            data = BarData(
                BarDataSet(barChartList, "Stats").apply {
                    setDrawValues(false)
                    color = ContextCompat.getColor(
                        requireContext(),
                        pokemon.dominant_color.parseToColor()
                    )
                    barShadowColor = CHART_BAR_SHADOW_COLOR
                }
            )
            legend.isEnabled = false
            description = null
            xAxis.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawAxisLine(true)
                setFitBars(true)
                textColor = getColor(R.color.old_silver)
                textSize = CHART_BAR_TEXT_SIZE
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return legendList.get(value.toInt())
                    }
                }
            }
            axisRight.apply {
                isEnabled = false
                granularity = CHART_BAR_GRANULARITY
                setDrawGridLines(false)
            }
            axisLeft.apply {
                isEnabled = false
                axisMinimum = CHART_BAR_AXIS_MIN
                granularity = CHART_BAR_GRANULARITY
                setDrawGridLines(false)
            }
            setPinchZoom(false)
            setDrawGridBackground(false)
            setDrawBarShadow(true)
            setDrawValueAboveBar(false)
            animateY(CHART_BAR_ANIMATION_DURATION)
        }.invalidate()
    }

    private fun setEvolutionsTabContent(pokemon: PokemonEntity) {
        with(binding.info.evolutionTabContent) {
            evolutionsAdapter = EvolutionsChainAdapter()
            evolutionsRecycler.adapter = evolutionsAdapter
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setPokemonTypes(types: List<TypeX>) {
        with(binding) {
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
                    initializeChart(pokemon)
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
        private const val FAVOURITE_MENU_TAB_ID = 101
        private val CHART_BAR_SHADOW_COLOR = Color.argb(40, 150, 150, 150)
        private const val CHART_BAR_ANIMATION_DURATION = 2000
        private const val CHART_BAR_GRANULARITY = 10F
        private const val CHART_BAR_AXIS_MIN = 0F
        private const val CHART_BAR_TEXT_SIZE = 14F

        fun newInstance(pokemon: PokemonEntity) = PokemonDetailsFragment().apply {
            val serializedPokemon = Json.encodeToString(pokemon)
            arguments = bundleOf(
                POKEMON_KEY to serializedPokemon
            )
        }
    }
}