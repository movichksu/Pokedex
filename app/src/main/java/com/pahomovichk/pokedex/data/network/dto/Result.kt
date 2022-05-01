package com.pahomovichk.pokedex.data.network.dto

import java.io.Serializable

data class Result(
    val name: String,
    val url: String,
    val id: Int,
    val height: Int,
    val weight: Int
): Serializable