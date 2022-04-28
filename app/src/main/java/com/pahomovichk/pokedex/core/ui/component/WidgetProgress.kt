package com.pahomovichk.pokedex.core.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.utils.extensions.gone
import com.pahomovichk.pokedex.core.utils.extensions.visible

class WidgetProgress(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.component_widget_progress, this)

        alpha = PROGRESS_BACKGROUND_ALPHA
        setBackgroundColor(ContextCompat.getColor(context, R.color.black))
        isClickable = true
        isFocusableInTouchMode = true
    }

    fun showProgress(show: Boolean) {
        if (show) {
            this.visible()
        } else {
            this.gone()
        }
    }

    companion object {
        private const val PROGRESS_BACKGROUND_ALPHA = 0.4f
    }
}