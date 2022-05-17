package com.pahomovichk.pokedex.presentation.showmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahomovichk.pokedex.core.utils.extensions.finally
import com.pahomovichk.pokedex.core.utils.extensions.onFailure
import com.pahomovichk.pokedex.core.utils.net.result.ResultResource
import com.pahomovichk.pokedex.domain.interactor.FileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonModelViewModel @Inject constructor(
    private val router: Router,
    private val interactor: FileInteractor
): ViewModel() {

    val loadPokemonLiveData = MutableLiveData<ResultResource<String>>()

    fun downloadImage(id: Int) {
        loadPokemonLiveData.value = ResultResource.InProgress
        viewModelScope.launch {
            interactor.saveFileToExternalStorage(id)
                .onFailure { Timber.e(it) }
                .finally { loadPokemonLiveData.postValue(it) }
        }
    }

    fun onBackPressed() {
        router.exit()
    }
}