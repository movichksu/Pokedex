package com.pahomovichk.pokedex.presentation.collection

import androidx.fragment.app.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.viewBinding
import com.pahomovichk.pokedex.databinding.FragmentCollectionBinding
import com.pahomovichk.pokedex.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment(R.layout.fragment_collection) {

    private val binding by viewBinding { FragmentCollectionBinding.bind(requireView()) }

    private val viewModel by viewModels<SearchViewModel>()

    override fun initUI() {
        // TODO("Not yet implemented")
    }

    override fun onBackPressed() {
       // TODO("Not yet implemented")
    }
}