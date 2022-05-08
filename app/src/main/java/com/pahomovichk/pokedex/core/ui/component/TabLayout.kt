package com.pahomovichk.pokedex.core.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.graphics.toColor
import com.google.android.material.tabs.TabLayout

class BrandedTabLayout(context: Context, attrs: AttributeSet?) :
    TabLayout(context, attrs) {

    private val tabs = mutableListOf<String>()
    private lateinit var tabListener: (Int) -> Unit

    init {
        tabMode = MODE_FIXED
        tabGravity = GRAVITY_FILL
        tabRippleColor = null
    }

    fun addTabs(vararg tabs: String, tabListener: (Int) -> Unit = {}) {
        if (tabs.size == 1) {
            throw IllegalArgumentException(
                "At least two tabs must be set. Current tabs count = ${tabs.size}"
            )
        }

        tabs.forEachIndexed { _, tabName ->
            newTab()
                .apply { text = tabName }
                .also { addTab(it) }
        }

        this.tabs.addAll(tabs)
        this.tabListener = tabListener

        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                tabListener.invoke(tab.position)
            }

            override fun onTabUnselected(tab: Tab) {}

            override fun onTabReselected(tab: Tab) {}
        })
    }

    fun applyColorScheme(dominantColor: Int) {
        setSelectedTabIndicatorColor(dominantColor)
        // TODO implement Checking theme to set colored selected tab
        setTabTextColors(Color.GRAY, dominantColor)
    }
}