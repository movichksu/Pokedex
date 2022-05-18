package com.pahomovichk.pokedex.presentation.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

}