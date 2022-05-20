package com.pahomovichk.pokedex.core.utils.extensions

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun String.capitalizeFirst() = this.capitalize()

fun String.getIdFromUrl(): String {
    return if (this.endsWith("/")) {
        this.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        this.takeLastWhile { it.isDigit() }
    }
}