package com.pahomovichk.pokedex.presentation.mainflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.ui.FlowFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentMainFlowBinding
import com.pahomovichk.pokedex.presentation.collection.CollectionFragment
import com.pahomovichk.pokedex.presentation.mainflow.model.BottomNavigationTabs.CATALOG_TAB_TAG
import com.pahomovichk.pokedex.presentation.mainflow.model.BottomNavigationTabs.COLLECTION_TAB_TAG
import com.pahomovichk.pokedex.presentation.mainflow.model.BottomNavigationTabs.CURRENT_TAB_TAG
import com.pahomovichk.pokedex.presentation.mainflow.model.BottomNavigationTabs.SEARCH_TAB_TAG
import com.pahomovichk.pokedex.presentation.pokemonlist.PokemonListFragment
import com.pahomovichk.pokedex.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFlowFragment : FlowFragment(R.layout.fragment_main_flow) {

    private val binding by viewBinding { FragmentMainFlowBinding.bind(requireView()) }

    private var currentTabTag = CATALOG_TAB_TAG

    private val viewModel by viewModels<MainFlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTabTag = savedInstanceState?.getString(CURRENT_TAB_TAG) ?: CATALOG_TAB_TAG
        // TODO replace checkDataLoaded and load remote pokemons here instead of activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainFlowBottomNavigation.setOnNavigationItemSelectedListener {
                val tag = when (it.itemId) {
                    R.id.item_catalog -> CATALOG_TAB_TAG
                    R.id.item_search -> SEARCH_TAB_TAG
                    R.id.item_collection -> COLLECTION_TAB_TAG
                    else -> throw UnsupportedOperationException("ItemId = ${it.itemId} not found!")
                }
                onBottomMenuItemSelected(tag)
                true
            }
            initBottomTabFragments()
            // TODO implement keyboardStateListener to hide bottomBar when needed
        }
    }

    private fun initBottomTabFragments() {
        // No need to recreate fragments when app died because its saved by the system
        if (childFragmentManager.fragments.isEmpty()) {
            val catalogFragment = PokemonListFragment()
            val searchFragment = SearchFragment()
            val collectionFragment = CollectionFragment()

            childFragmentManager.beginTransaction()
                .add(R.id.flowContainer_fl, catalogFragment, CATALOG_TAB_TAG)
                .add(R.id.flowContainer_fl, searchFragment, SEARCH_TAB_TAG)
                .add(R.id.flowContainer_fl, collectionFragment, COLLECTION_TAB_TAG)
                .hide(searchFragment)
                .hide(collectionFragment)
                .commitNow()
        }
        onBottomMenuItemSelected(currentTabTag)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(CURRENT_TAB_TAG, currentTabTag)
    }

    override fun onBackPressed() {
        val fragment = childFragmentManager.findFragmentByTag(currentTabTag) as BaseFragment?
        fragment?.onBackPressed() ?: super.onBackPressed()
    }

    private fun onBottomMenuItemSelected(tag: String) {
        if (tag == currentTabTag) {
            return
        }

        childFragmentManager.apply {
            findFragmentByTag(currentTabTag)?.let { currentFragment ->
                findFragmentByTag(tag)?.let { selectedFragment ->
                    beginTransaction()
                        .setCustomAnimations(
                            R.animator.slide_left_enter,
                            R.animator.fade_out_exit
                        )
                        .hide(currentFragment)
                        .show(selectedFragment)
                        .commit()
                    currentTabTag = tag
                }
            }
        }
    }

    private fun changeBottomTab(tag: String) {
        binding.mainFlowBottomNavigation.selectedItemId = when (tag) {
            CATALOG_TAB_TAG -> R.id.item_catalog
            SEARCH_TAB_TAG -> R.id.item_search
            COLLECTION_TAB_TAG -> R.id.item_collection
            else -> throw IllegalArgumentException()
        }
    }

    companion object {

        private const val IS_DATA_LOADED = "IS_DATA_LOADED"
    }
}