package com.pahomovichk.pokedex.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.app.Screens
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel@Inject constructor(
    private val router: Router,
    private val interactor: PokemonDbInteractor
): ViewModel() {

    @PokemonInfoTabIndex
    var currentTabIndex = ABOUT_TAB_INDEX

    fun onTabSelected(@PokemonInfoTabIndex selectedTabIndex: Int) {
        currentTabIndex = selectedTabIndex
    }

    fun onHeartClicked(id: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                interactor.addToFavourites(id)
            } else {
                interactor.removeFromFavourites(id)
            }
        }
    }

    fun onOpenModelClicked(pokemon: PokemonEntity) {
        router.navigateTo(Screens.PokemonModel(pokemon))
    }

    fun onBackPressed() {
        router.exit()
    }
}