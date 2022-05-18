package com.pahomovichk.pokedex.core.utils.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.doOnLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun View.goneUnless(predicate: Boolean) = if (predicate) this.visible() else this.gone()

fun View.invisibleUnless(predicate: Boolean) = if (predicate) this.visible() else this.invisible()

fun <T : View> T.visible() = this.apply { this.visibility = View.VISIBLE }

fun <T : View> T.invisible() = this.apply { this.visibility = View.INVISIBLE }

fun <T : View> T.gone() = this.apply { this.visibility = View.GONE }

fun View.keyboardStateListener(
    owner: LifecycleOwner,
    keyboardCallback: (visible: Boolean) -> Unit
) {
    doOnLayout { view ->
        var isKeyboardVisible =
            ViewCompat.getRootWindowInsets(view)?.getInsets(ime())?.bottom ?: 0 != 0
        keyboardCallback(isKeyboardVisible)

        viewTreeObserver.addOnGlobalLayoutListener {
            if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                return@addOnGlobalLayoutListener
            }
            ViewCompat.getRootWindowInsets(view).apply {
                val keyboardUpdateCheck = this?.getInsets(ime())?.bottom ?: 0 != 0
                if (isKeyboardVisible != keyboardUpdateCheck) {
                    keyboardCallback(keyboardUpdateCheck)
                    isKeyboardVisible = keyboardUpdateCheck
                }
            }
        }
    }
}