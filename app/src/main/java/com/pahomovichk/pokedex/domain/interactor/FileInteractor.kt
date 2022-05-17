package com.pahomovichk.pokedex.domain.interactor

import com.pahomovichk.pokedex.core.utils.net.result.ResultResource

interface FileInteractor {

    suspend fun saveFileToExternalStorage(id: Int): ResultResource<String>
}