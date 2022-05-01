package com.pahomovichk.pokedex.di

import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import com.pahomovichk.pokedex.domain.interactor.impl.PokemonInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokeApi: PokeApi
    ): PokemonInteractor = PokemonInteractorImpl(pokeApi)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
}