package com.pahomovichk.pokedex.di

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.core.utils.net.ResultAdapterFactory
import com.pahomovichk.pokedex.data.network.api.PokeApi
import com.pahomovichk.pokedex.domain.interactor.PokemonInteractor
import com.pahomovichk.pokedex.domain.interactor.impl.PokemonInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokeApi: PokeApi,
        dispatcher: DispatcherProvider
    ): PokemonInteractor = PokemonInteractorImpl(pokeApi, dispatcher)

    @Singleton
    @Provides
    fun providePokeApi(
        json: Json
    ): PokeApi =
        Retrofit.Builder()
            .addCallAdapterFactory(ResultAdapterFactory(json))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)

    @Provides
    @Singleton
    fun provide(): DispatcherProvider =
        object : DispatcherProvider {
            override val default: CoroutineDispatcher = Dispatchers.Default
            override val io: CoroutineDispatcher = Dispatchers.IO
            override val main: CoroutineDispatcher = Dispatchers.Main
        }
}