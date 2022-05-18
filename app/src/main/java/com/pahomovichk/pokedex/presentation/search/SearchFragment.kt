package com.pahomovichk.pokedex.presentation.search

import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding { FragmentSearchBinding.bind(requireView()) }

    private val viewModel by viewModels<SearchViewModel>()

    override fun initUI() {
        // TODO
    }

    override fun onBackPressed() {
        // TODO
    }
}