package com.pahomovichk.pokedex.presentation.search

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.databinding.FragmentSearchBinding
import com.pahomovichk.pokedex.presentation.pokemonlist.adapter.PokemonItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding { FragmentSearchBinding.bind(requireView()) }

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var pokemonItemAdapter: PokemonItemAdapter

    override fun onFirstLaunch() {
        super.onFirstLaunch()
        viewModel.onFirstLaunch()
    }

    override fun initUI() {
        with(binding) {
            pokemonItemAdapter = PokemonItemAdapter(
                { id -> viewModel.onPokemonItemClicked(id) },
                { id, isFavourite ->
                    pokemonItemAdapter.changeItem(id, isFavourite)
                    viewModel.onHeartClicked(id, isFavourite)
                }
            )
            pokemonRecycler.adapter = pokemonItemAdapter
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
            }

            searchEditText.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (text.toString().isEmpty()) {
                        pokemonRecycler.gone()
                        emptySearchLayout.visible()
                    } else {
                        cancelButton.visible()
                        viewModel.onSearchPokemon(text.toString())
                    }
                }
            }
            cancelButton.setOnClickListener {
                cancelButton.gone()
                searchEditText.text!!.clear()
                pokemonItemAdapter.setAllItems(listOf())
                binding.emptySearchLayout.visible()
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
                    .onSuccess { viewModel.setBaseList(it) }
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
            filteredListLiveData.observe(viewLifecycleOwner) { list ->
                println("POKEMON LIST IS $list")
                pokemonItemAdapter.setAllItems(list)
                if (list.isEmpty()) {
                    binding.emptySearchLayout.gone()
                    binding.noResultsSearchLayout.visible()
                    binding.pokemonRecycler.gone()
                } else {
                    binding.pokemonRecycler.visible()
                    binding.emptySearchLayout.gone()
                    binding.noResultsSearchLayout.gone()
                }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}