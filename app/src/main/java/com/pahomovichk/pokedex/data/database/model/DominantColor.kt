package com.pahomovichk.pokedex.data.database.model

import kotlinx.serialization.Serializable

@Serializable
enum class DominantColor(private val key: String) {
    BLACK("black"),
    BLUE("blue"),
    BROWN("brown"),
    GRAY("gray"),
    GREEN("green"),
    PINK("pink"),
    PURPLE("purple"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    override fun toString() = key
}