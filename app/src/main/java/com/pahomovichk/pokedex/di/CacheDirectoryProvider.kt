package com.pahomovichk.pokedex.di

import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheDirectoryProvider {

    @CacheDirectory
    @Provides
    @Singleton
    fun provideCacheDirectory(): String {
        val rootCacheDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val pokemonsDirectory = File(rootCacheDirectory, POKEDEX_DIRECTORY)
        if (!pokemonsDirectory.exists()) {
            pokemonsDirectory.mkdir()
        }

        return pokemonsDirectory.absolutePath
    }

    companion object {
        private const val POKEDEX_DIRECTORY = "Pokedex"
    }
}