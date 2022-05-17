package com.pahomovichk.pokedex.di

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.data.database.AppDatabase
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.domain.interactor.FileInteractor
import com.pahomovichk.pokedex.domain.interactor.PokemonDbInteractor
import com.pahomovichk.pokedex.domain.interactor.PokemonNetworkInteractor
import com.pahomovichk.pokedex.domain.interactor.impl.FileInteractorImpl
import com.pahomovichk.pokedex.domain.interactor.impl.PokemonDbInteractorImpl
import com.pahomovichk.pokedex.domain.interactor.impl.PokemonNetworkInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideNetworkInteractor(
        pokeApi: PokeApi,
        dispatcher: DispatcherProvider
    ): PokemonNetworkInteractor =
        PokemonNetworkInteractorImpl(pokeApi, dispatcher)

    @Singleton
    @Provides
    fun provideDbInteractor(
        database: AppDatabase,
        dispatcher: DispatcherProvider
    ): PokemonDbInteractor =
        PokemonDbInteractorImpl(database.pokemonsDao(), dispatcher)

    @Singleton
    @Provides
    fun provideFileInteractor(
        dispatcher: DispatcherProvider,
        @CacheDirectory cacheDirectory: String
    ): FileInteractor =
        FileInteractorImpl(dispatcher, cacheDirectory)
}