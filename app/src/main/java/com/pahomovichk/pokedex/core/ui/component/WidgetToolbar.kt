package com.pahomovichk.pokedex.core.ui.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.appbar.MaterialToolbar
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.utils.extensions.gone
import com.pahomovichk.pokedex.core.utils.extensions.visible

/**
 * A custom view for all types of toolbar in the app
 * Use the toolbar_title attribute to set the title of toolbar
 * Use toolbar_end_text attribute to set the text at the end of the toolbar
 * Attribute toolbar_color_scheme has two parameters:
 *      1) transparent - the standard color of toolbar
 *      2) white
 * Use toolbar_type to define the type of toolbar
 *      1) simple - toolbar without any icons or text
 *      2) back_arrow - toolbar with navigation back-icon
 *      3) back_arrow_and_more - back-arrow toolbar with
 */
class WidgetToolbar(context: Context, attrs: AttributeSet?) :
    MaterialToolbar(context, attrs) {

    private var endIcon: ImageView = ImageView(context)

    private var endIconListener: (() -> Unit)? = null
    private var toolbarTitle: String? = null
    private lateinit var toolbarColorScheme: ToolbarColorScheme
    private lateinit var toolbarType: ToolbarType

    init {
        setTitleTextAppearance(context, R.style.TextAppearance_Bold_14)
        isTitleCentered = true
        parseAttributes(attrs)
        setToolbarType()
        applyToolbarColors()
    }

    fun getEndIconView() = endIcon

    fun setOnEndIconClickListener(listener: () -> Unit) {
        endIconListener = listener
    }

    fun setEndIconVisibility(isVisible: Boolean) {
        if (isVisible) endIcon.visible()
        else endIcon.gone()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray =
                context.theme.obtainStyledAttributes(it, R.styleable.WidgetToolbar, 0, 0)

            try {
                toolbarType =
                    ToolbarType.values()[
                            typedArray.getInt(R.styleable.WidgetToolbar_toolbar_type, 0)
                    ]
                toolbarColorScheme =
                    ToolbarColorScheme.values()[
                            typedArray.getInt(R.styleable.WidgetToolbar_toolbar_color_scheme, 0)
                    ]
                toolbarTitle = typedArray.getString(R.styleable.WidgetToolbar_toolbar_title)
            } finally {
                typedArray.recycle()
            }
        }

        title = toolbarTitle
    }

    private fun setToolbarType() {
        when (toolbarType) {
            ToolbarType.BACK_ARROW -> {
                setNavigationIcon(R.drawable.ic_arrow_back)
                setNavigationIconTint(getColor(context, R.color.light_grey))
            }
            ToolbarType.BACK_ARROW_AND_MORE -> {
                setNavigationIcon(R.drawable.ic_arrow_back)
                setEndIcon(R.drawable.ic_menu)
                setNavigationIconTint(getColor(context, R.color.light_grey))
            }
            ToolbarType.BACK_ARROW_AND_DOWNLOAD -> {
                setNavigationIcon(R.drawable.ic_arrow_back)
                setEndIcon(R.drawable.ic_download)
                setNavigationIconTint(getColor(context, R.color.light_grey))
            }
            ToolbarType.SIMPLE -> { }
        }
    }

    private fun setEndIcon(resource: Int) {
        endIcon
            .apply {
                layoutParams = createLayoutParams()
                background =
                    ContextCompat.getDrawable(
                        context,
                        resource
                    )
            }.also { icon ->
                addView(icon)
            }.setOnClickListener {
                endIconListener?.invoke()
            }
    }

    private fun createLayoutParams(): LayoutParams =
        LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.END
        }

    private fun applyToolbarColors() {
        val whiteColor = ContextCompat.getColor(context, R.color.white)
        val transparentColor = Color.TRANSPARENT

        when (toolbarColorScheme) {
            ToolbarColorScheme.TRANSPARENT -> {
                setBackgroundColor(transparentColor)
            }
            ToolbarColorScheme.WHITE -> {
                setBackgroundColor(whiteColor)
                elevation = TOOLBAR_ELEVATION
            }
        }
    }

    private companion object {
        private const val TOOLBAR_ELEVATION = 8F
    }
}

private enum class ToolbarColorScheme {
    TRANSPARENT,
    WHITE
}

private enum class ToolbarType {
    SIMPLE,
    BACK_ARROW,
    BACK_ARROW_AND_MORE,
    BACK_ARROW_AND_DOWNLOAD
}