package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonInteractorImpl @Inject constructor(
    private val pokeApi: PokeApi
): PokemonInteractor