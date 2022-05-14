package com.pahomovichk.pokedex.core.ui.coloring

import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.data.database.model.DominantColor

fun String.typeToColor(): Int =
    when (this) {
        "normal" -> R.color.type_normal
        "fighting" -> R.color.type_fight
        "flying" -> R.color.type_flying
        "poison" -> R.color.type_poison
        "ground" -> R.color.type_ground
        "rock" -> R.color.type_rock
        "bug" -> R.color.type_bug
        "ghost" -> R.color.type_ghost
        "steel" -> R.color.type_steal
        "fire" -> R.color.type_fire
        "water" -> R.color.type_water
        "grass" -> R.color.type_grass
        "electric" -> R.color.type_electric
        "psychic" -> R.color.type_psychic
        "ice" -> R.color.type_ice
        "dragon" -> R.color.type_dragon
        "dark" -> R.color.type_dark
        "fairy" -> R.color.type_fairy
        else -> R.color.type_normal
    }

fun DominantColor.parseToColor(): Int =
    when(this) {
        DominantColor.BLACK -> R.color.type_dark
        DominantColor.BLUE -> R.color.type_water
        DominantColor.BROWN -> R.color.type_rock
        DominantColor.GRAY -> R.color.type_normal
        DominantColor.GREEN -> R.color.type_grass
        DominantColor.PINK -> R.color.type_psychic
        DominantColor.PURPLE -> R.color.type_ghost
        DominantColor.RED -> R.color.type_fire
        DominantColor.WHITE -> R.color.type_steal
        DominantColor.YELLOW -> R.color.type_electric
        else -> R.color.type_normal
    }