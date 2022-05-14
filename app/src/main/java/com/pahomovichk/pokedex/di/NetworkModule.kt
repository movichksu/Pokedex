package com.pahomovichk.pokedex.di

import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.core.utils.net.ResultAdapterFactory
import com.pahomovichk.pokedex.data.network.api.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
    fun providePokeApi(
        json: Json
    ): PokeApi =
        Retrofit.Builder()
            .addCallAdapterFactory(ResultAdapterFactory(json))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
}