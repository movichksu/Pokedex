package com.pahomovichk.pokedex.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.AnimatedSupportAppNavigator
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    private val navigator: Navigator =
        AnimatedSupportAppNavigator(this, supportFragmentManager, R.id.appContainer)


    private val currentFlowFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.appContainer) as? BaseFragment

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            viewModel.onLaunch()
        }
    }

    fun showProgress(shouldShow: Boolean) {
        binding.activityProgress.showProgress(shouldShow)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onBackPressed() {
        currentFlowFragment?.onBackPressed() ?: super.onBackPressed()
    }
}