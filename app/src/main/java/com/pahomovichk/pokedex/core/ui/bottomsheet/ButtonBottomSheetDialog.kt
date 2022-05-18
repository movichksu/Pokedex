package com.pahomovichk.pokedex.core.ui.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.bottomsheet.model.ButtonBottomSheetModel
import com.pahomovichk.pokedex.core.ui.coloring.parseToColor
import com.pahomovichk.pokedex.core.utils.extensions.*
import com.pahomovichk.pokedex.databinding.BottomSheetButtonBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

open class ButtonBottomSheetDialog : BaseBottomSheetDialog() {

    override val layoutRes: Int = R.layout.bottom_sheet_button

    private val binding by viewBinding { BottomSheetButtonBinding.bind(requireView()) }

    private var onPositiveButtonClickAction: (() -> Unit) = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialogModel = Json.decodeFromString(tryToGetString(EXTRA_MODEL)) as ButtonBottomSheetModel

        with(binding) {
            with(dialogModel) {

                iconId?.let {
                    bottomSheetButtonLabelImageView.setImageResource(it)
                    bottomSheetButtonLabelImageView.visible()
                }
                bottomSheetButtonTitle.text = title
                message?.let {
                    bottomSheetButtonMessage.visible()
                    bottomSheetButtonMessage.text = it
                }
                positiveButtonTextId?.let {
                    bottomSheetButtonPositiveButton.setText(it)
                    bottomSheetButtonPositiveButton.visible()
                }
                bottomSheetButtonPositiveButton.apply {
                    setBackgroundColor(getColor(dominantColor.parseToColor()))
                    setOnClickListener {
                        dismiss()
                        onPositiveButtonClickAction.invoke()
                    }
                }
                bottomSheetButtonCloseImageView.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    fun setPositiveClickListener(onPositiveButtonClick: () -> Unit) {
        onPositiveButtonClickAction = onPositiveButtonClick
    }

    companion object {

        private const val EXTRA_MODEL = "EXTRA_MODEL"

        fun newInstance(dialogModel: ButtonBottomSheetModel): ButtonBottomSheetDialog =
            ButtonBottomSheetDialog().apply {
                val serializedModel = Json.encodeToString(dialogModel)
                arguments = bundleOf(
                    EXTRA_MODEL to serializedModel
                )
            }
    }
}