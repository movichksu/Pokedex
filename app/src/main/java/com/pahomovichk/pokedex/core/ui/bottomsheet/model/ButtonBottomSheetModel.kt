package com.pahomovichk.pokedex.core.ui.bottomsheet.model

import com.pahomovichk.pokedex.data.database.model.DominantColor
import kotlinx.serialization.Serializable

@Serializable
data class ButtonBottomSheetModel(
    val iconId: Int?,
    val message: String?,
    val title: String,
    val dominantColor: DominantColor,
    val positiveButtonTextId: Int?
)