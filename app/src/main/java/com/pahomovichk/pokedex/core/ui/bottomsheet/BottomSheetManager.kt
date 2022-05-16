package com.pahomovichk.pokedex.core.ui.bottomsheet

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.pahomovichk.pokedex.core.ui.bottomsheet.model.ButtonBottomSheetModel
import com.pahomovichk.pokedex.data.database.model.DominantColor

object BottomSheetManager {

    fun showSimpleBottomSheet(
        fragmentManager: FragmentManager,
        iconId: Int? = null,
        title: String,
        message: String? = null,
        dominantColor: DominantColor = DominantColor.BLACK,
        @StringRes positiveButtonTextId: Int? = null,
        onPositiveClickAction: () -> Unit = {}
    ) {

        val model = ButtonBottomSheetModel(
            iconId,
            message,
            title,
            dominantColor,
            positiveButtonTextId
        )

        ButtonBottomSheetDialog
            .newInstance(model)
            .apply {
                setPositiveClickListener(onPositiveClickAction)
                show(fragmentManager)
            }
    }
}