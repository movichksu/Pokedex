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
        DominantColor.BLACK -> R.color.dominant_black
        DominantColor.BLUE -> R.color.dominant_blue
        DominantColor.BROWN -> R.color.dominant_brown
        DominantColor.GRAY -> R.color.dominant_gray
        DominantColor.GREEN -> R.color.dominant_green
        DominantColor.PINK -> R.color.dominant_pink
        DominantColor.PURPLE -> R.color.dominant_purple
        DominantColor.RED -> R.color.dominant_red
        DominantColor.WHITE -> R.color.dominant_white
        DominantColor.YELLOW -> R.color.dominant_yellow
        else -> R.color.dominant_gray
    }