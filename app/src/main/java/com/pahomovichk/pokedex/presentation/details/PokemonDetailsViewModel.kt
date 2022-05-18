package com.pahomovichk.pokedex.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.app.Screens
import com.pahomovichk.pokedex.core.utils.extensions.finally
import com.pahomovichk.pokedex.core.utils.extensions.map
import com.pahomovichk.pokedex.core.utils.extensions.onFailure
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.data.model.EvolutionChainItem
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import com.pahomovichk.pokedex.domain.interactor.PokemonNetworkInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val router: Router,
    private val dbInteractor: PokemonDbInteractor,
    private val networkInteractor: PokemonNetworkInteractor
) : ViewModel() {

    val chainMutableLiveData = MutableLiveData<ResultResource<List<EvolutionChainItem>>>()

    @PokemonInfoTabIndex
    var currentTabIndex = ABOUT_TAB_INDEX

    fun onTabSelected(@PokemonInfoTabIndex selectedTabIndex: Int) {
        currentTabIndex = selectedTabIndex
    }

    fun getEvolutionChain(chainId: Int) {
        chainMutableLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            networkInteractor.getEvolutionChain(chainId)
                .map { evolutionChain ->
                    val evolutionsList = arrayListOf<EvolutionChainItem>()
                    with(evolutionChain.chain) {
                        if (evolves_to.isNotEmpty()) {
                            if (evolves_to.size == 1) {
                                evolves_to.get(0).let { firstEvolve ->
                                    evolutionsList.add(
                                        EvolutionChainItem(
                                            getId(species.url).toInt(),
                                            species.name,
                                            getId(firstEvolve.species.url).toInt(),
                                            firstEvolve.species.name
                                        )
                                    )
                                    if (firstEvolve.evolves_to.isNotEmpty()) {
                                        firstEvolve.evolves_to.forEach { evolve ->
                                            evolutionsList.add(
                                                EvolutionChainItem(
                                                    getId(species.url).toInt(),
                                                    species.name,
                                                    getId(evolve.species.url).toInt(),
                                                    evolve.species.name
                                                )
                                            )
                                        }
                                    }
                                }
                            } else {
                                evolves_to.forEach { evolve ->
                                    evolutionsList.add(
                                        EvolutionChainItem(
                                            getId(species.url).toInt(),
                                            species.name,
                                            getId(evolve.species.url).toInt(),
                                            evolve.species.name
                                        )
                                    )
                                }
                            }
                        }
                    }
                    evolutionsList
                }
                .onFailure { Timber.e(it) }
                .finally { chainMutableLiveData.postValue(it) }
        }
    }

    fun onHeartClicked(id: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                dbInteractor.addToFavourites(id)
            } else {
                dbInteractor.removeFromFavourites(id)
            }
        }
    }

    fun onOpenModelClicked(pokemon: PokemonEntity) {
        router.navigateTo(Screens.PokemonModel(pokemon))
    }

    fun onBackPressed() {
        router.exit()
    }

    private fun getId(url: String): String {
        return if (url.endsWith("/")) {
            url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            url.takeLastWhile { it.isDigit() }
        }
    }
}