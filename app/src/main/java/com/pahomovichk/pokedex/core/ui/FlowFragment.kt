package com.pahomovichk.pokedex.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.AnimatedSupportAppNavigator
import com.pahomovichk.pokedex.di.FlowRouterQualifier
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class FlowFragment : BaseFragment {

    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    constructor() : super()

    @Inject
    @FlowRouterQualifier
    lateinit var navigatorHolder: NavigatorHolder

    protected val navigator by lazy {
        AnimatedSupportAppNavigator(requireActivity(), childFragmentManager, R.id.flowContainer_fl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_flow, container, false)
    }

    private val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.fragmentContainer) as? BaseFragment

    override fun onResume() {
        super.onResume()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun initUI() {}

    override fun onBackPressed() {
        currentFragment?.onBackPressed()
    }
}