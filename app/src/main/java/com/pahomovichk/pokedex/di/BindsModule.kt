package com.pahomovichk.pokedex.di

import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import com.pahomovichk.pokedex.domain.interactor.impl.PokemonInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

//    @Singleton
//    @Binds
//    fun bindInteractor(impl: PokemonInteractorImpl): PokemonInteractor
}