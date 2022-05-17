package com.pahomovichk.pokedex.presentation.collection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

}