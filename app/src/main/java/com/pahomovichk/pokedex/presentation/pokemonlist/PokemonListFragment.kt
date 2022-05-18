package com.pahomovichk.pokedex.presentation.pokemonlist

import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.databinding.FragmentPokemonListBinding
import com.pahomovichk.pokedex.presentation.pokemonlist.adapter.PokemonItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : BaseFragment(R.layout.fragment_pokemon_list) {

    private val binding by viewBinding { FragmentPokemonListBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonListViewModel>()

    private lateinit var pokemonItemAdapter: PokemonItemAdapter

    override fun onFirstLaunch() {
        super.onFirstLaunch()
        viewModel.onFirstLaunch()
    }

    override fun initUI() {
        with(binding) {
            pokemonItemAdapter = PokemonItemAdapter(
                { id -> viewModel.onPokemonItemClicked(id) },
                { id, isFavourite -> viewModel.onHeartClicked(id, isFavourite) }
            )
            pokemonRecycler.adapter = pokemonItemAdapter
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun initVM() {
        super.initVM()
        with(viewModel) {
            pokemonListLiveData.observe(viewLifecycleOwner) { result ->
                showProgress(false)
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { list ->
                        pokemonItemAdapter.setItems(list)
                    }
                    .onFailure {
                        requireContext().toast(
                            getString(R.string.error_message_getting_data_from_db)
                        )
                    }
            }
            getPokemonEntityLiveEvent.observe(viewLifecycleOwner) { result ->
                showProgress(false)
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { onGotPokemonEntity(it) }
                    .onFailure {
                        requireContext().toast(
                            getString(R.string.error_message_getting_data_from_db)
                        )
                    }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}