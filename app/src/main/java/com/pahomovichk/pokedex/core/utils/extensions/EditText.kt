package com.pahomovichk.pokedex.core.utils.extensions

import android.widget.EditText

fun EditText.clear() {
    text = null
}

fun EditText.getTrimmedText() = text.trim().toString()
