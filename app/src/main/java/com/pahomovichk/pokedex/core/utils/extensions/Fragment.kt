package com.pahomovichk.pokedex.core.utils.extensions

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Fragment.tryToGetString(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): String = arguments?.getString(key) ?: throwArgumentException(exceptionMessage)

fun Fragment.tryToGetInt(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Int = arguments?.getInt(key) ?: throwArgumentException(exceptionMessage)

fun Fragment.tryToGetBoolean(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Boolean = arguments?.getBoolean(key) ?: throwArgumentException(exceptionMessage)

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.tryToGetSerializable(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): T = (arguments?.getSerializable(key) as? T) ?: throwArgumentException(exceptionMessage)

fun Fragment.getColor(@ColorRes id: Int) = ContextCompat.getColor(requireContext(), id)

/**
 * Связывание вьюбиндинга с жизненным циклом фрагмента
 *
 * @see <a href="https://gist.github.com/rishabhkohli/d4a4e49049b0a3578cb4347191a78de5">Fragment.viewLifecycleAware</a>
 */
fun <T> Fragment.viewBinding(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        // This is called JUST before onDestroyView in a Fragment as a limitation of the lifecycle
        //  library. Do not try to access this property in onDestroyView, as it would
        //  implicitly call the initialise function again and provide a new value.
        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
            this@viewBinding.lifecycle.removeObserver(this)
            super.onDestroy(owner)
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding
                ?: initialise().also {
                    binding = it
                    this@viewBinding.viewLifecycleOwner.lifecycle.addObserver(this)
                }
    }

private fun <T> throwArgumentException(message: String): T = throw IllegalArgumentException(message)

private fun composeErrorMessage(key: String) = "Argument $key hasn't been provided"