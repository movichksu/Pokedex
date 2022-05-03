package com.pahomovichk.pokedex.presentation.details

import androidx.annotation.IntDef

const val ABOUT_TAB_INDEX: Int = 0
const val STATS_TAB_INDEX: Int = 1
const val EVOLUTIONS_TAB_INDEX: Int = 2

@IntDef(value = [ABOUT_TAB_INDEX, STATS_TAB_INDEX, EVOLUTIONS_TAB_INDEX])
@Retention(AnnotationRetention.SOURCE)
annotation class PokemonInfoTabIndex