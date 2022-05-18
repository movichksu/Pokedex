package com.pahomovichk.pokedex.data.model

data class EvolutionChainItem(
    val parentId: Int,
    val parentName: String,
    val childId: Int,
    val childName: String
)