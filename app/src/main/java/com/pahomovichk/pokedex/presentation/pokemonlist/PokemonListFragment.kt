package com.pahomovichk.pokedex.presentation.pokemonlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentPokemonListBinding
import com.pahomovichk.pokedex.presentation.pokemonlist.adapter.PokemonItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment: BaseFragment(R.layout.fragment_pokemon_list) {

    private val binding by viewBinding { FragmentPokemonListBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonListViewModel>()

    private lateinit var pokemonItemAdapter: PokemonItemAdapter

    override fun onFirstLaunch() {
        super.onFirstLaunch()
        viewModel.onFirstLaunch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUI() {
        with(binding) {
            pokemonItemAdapter = PokemonItemAdapter { id, dominantColor ->
                viewModel.onPokemonItemClicked(id, dominantColor)
            }
            pokemonRecycler.adapter = pokemonItemAdapter
        }
    }

    override fun initVM() {
        super.initVM()
        with(viewModel) {
            pokemonListLiveData.observe(viewLifecycleOwner) { list ->
                pokemonItemAdapter.setItems(list)
            }
        }
    }

    override fun onBackPressed() {
        // TODO
    }
}