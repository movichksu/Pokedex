package com.pahomovichk.pokedex.di

import android.content.Context
import androidx.room.Room
import com.pahomovichk.pokedex.core.utils.Constants
import com.pahomovichk.pokedex.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.POKEMONS_TABLE_NAME
        )
        .build()
}