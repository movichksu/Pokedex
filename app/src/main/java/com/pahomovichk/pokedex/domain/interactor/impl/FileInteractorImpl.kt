package com.pahomovichk.pokedex.domain.interactor.impl

import com.pahomovichk.pokedex.core.utils.coroutines.DispatcherProvider
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.di.CacheDirectory
import com.pahomovichk.pokedex.domain.interactor.FileInteractor
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import javax.inject.Inject
import com.pahomovichk.pokedex.core.utils.getPokemonLargePngImage
import java.net.URL

class FileInteractorImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    @CacheDirectory private val cacheDirectory: String
) : FileInteractor {

    private fun getModelPath(pokemonId: Int) =
        File(
            cacheDirectory
                .plus(File.separator)
                .plus(FILE_NAME + pokemonId + FILE_EXTENSION)
        ).absolutePath

    override suspend fun saveFileToExternalStorage(id: Int): ResultResource<String> =
        withContext(dispatchers.io) {
            try {
                val target = File(
                    cacheDirectory,
                    FILE_NAME + id + FILE_EXTENSION
                )

                if (!isFileExists(target.absolutePath)) {
                    URL(getPokemonLargePngImage(id.toString())).openStream().use { input ->
                        FileOutputStream(getModelPath(id)).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
                return@withContext ResultResource.Success.Value(target.absolutePath)
            } catch (e: Exception) {
                return@withContext ResultResource.Failure.Error(e)
            }
        }

    private fun isFileExists(uri: String): Boolean = File(uri).exists()

    companion object {
        private const val FILE_NAME = "pokemon"
        private const val FILE_EXTENSION = ".png"
    }
}