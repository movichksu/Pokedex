package com.pahomovichk.pokedex.core.ui.coloring

import com.pahomovichk.pokedex.R

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

fun String.parseToColor(): Int =
    when(this) {
        "black" -> R.color.type_dark
        "blue" -> R.color.type_water
        "brown" -> R.color.type_rock
        "gray" -> R.color.type_normal
        "green" -> R.color.type_grass
        "pink" -> R.color.type_psychic
        "purple" -> R.color.type_ghost
        "red" -> R.color.type_fire
        "white" -> R.color.type_steal
        "yellow" -> R.color.type_electric
        else -> R.color.type_normal
    }