package com.pahomovichk.pokedex.presentation.showmodel

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.ui.bottomsheet.BottomSheetManager
import com.pahomovichk.pokedex.core.ui.coloring.parseToColor
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.core.utils.getPokemonLargePngImage
import com.pahomovichk.pokedex.data.database.model.PokemonEntity
import com.pahomovichk.pokedex.databinding.FragmentPokemonModelBinding
import com.pahomovichk.pokedex.presentation.details.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class PokemonModelFragment: BaseFragment(R.layout.fragment_pokemon_model) {

    private val binding by viewBinding { FragmentPokemonModelBinding.bind(requireView()) }

    private val viewModel by viewModels<PokemonModelViewModel>()

    private val pokemon : PokemonEntity
            by lazy {  Json.decodeFromString(tryToGetString(POKEMON_KEY)) }

    private val permissionRequester =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> { viewModel.downloadImage(pokemon.id) }
                !shouldShowRequestPermissionRationale(EXTERNAL_STORAGE_PERMISSION) -> {
                    showAskPermissionBottomSheet(R.string.dont_ask_again_permission_button) {
                        openSettings()
                    }
                }
                else -> {
                    showAskPermissionBottomSheet(R.string.rationale_permission_button) {
                        requestPermissions()
                    }
                }
            }
        }

    override fun initUI() {
        with(binding) {
            root.setBackgroundColor(getColor(pokemon.dominant_color.parseToColor()))
            toolbar.setNavigationOnClickListener { onBackPressed() }
            toolbar.setOnEndIconClickListener {
                requestPermissions()
            }

            Glide
                .with(requireContext())
                .load(getPokemonLargePngImage(pokemon.id.toString()))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(pokemonImage)
        }
    }

    override fun initVM() {
        super.initVM()
        with(viewModel) {
            loadPokemonLiveData.observe(viewLifecycleOwner) { result ->
                showProgress(false)
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { path ->
                        requireContext().toast(
                            getString(R.string.toast_message_file_loaded_successfully, path)
                        )
                    }
                    .onFailure {
                        requireContext().toast(
                            getString(R.string.error_message_fail_to_load_image)
                        )
                    }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    private fun requestPermissions() {
            permissionRequester.launch(EXTERNAL_STORAGE_PERMISSION)
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireActivity().packageName, null))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showAskPermissionBottomSheet(
        positiveButtonTextId: Int,
        onPositiveButtonClickListener: () -> Unit
    ) {
        BottomSheetManager.showSimpleBottomSheet(
            fragmentManager = childFragmentManager,
            title = getString(R.string.external_storage_permission_title),
            message = getString(R.string.external_storage_permission_message),
            dominantColor = pokemon.dominant_color,
            positiveButtonTextId = positiveButtonTextId,
            onPositiveClickAction = onPositiveButtonClickListener
        )
    }

    companion object {
        private const val POKEMON_KEY = "POKEMON_KEY"
        private const val EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        fun newInstance(pokemon: PokemonEntity) = PokemonModelFragment().apply {
            val serializedPokemon = Json.encodeToString(pokemon)
            arguments = bundleOf(
                POKEMON_KEY to serializedPokemon
            )
        }
    }
}