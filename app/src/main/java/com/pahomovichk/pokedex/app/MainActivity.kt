package com.pahomovichk.pokedex.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.AnimatedSupportAppNavigator
import com.pahomovichk.pokedex.core.ui.BaseFragment
import com.pahomovichk.pokedex.core.utils.extensions.onFailure
import com.pahomovichk.pokedex.core.utils.extensions.onProgress
import com.pahomovichk.pokedex.core.utils.extensions.onSuccess
import com.pahomovichk.pokedex.core.utils.extensions.toast
import com.pahomovichk.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val viewModel by viewModels<MainActivityViewModel>()

    private val navigator: Navigator =
        AnimatedSupportAppNavigator(this, supportFragmentManager, R.id.appContainer)

    private val currentFlowFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.appContainer) as? BaseFragment

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.onFirstLaunch()
        }
        initVM()
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

    fun showProgress(shouldShow: Boolean) {
        binding.activityProgress.showProgress(shouldShow)
    }

    private fun initVM() {
        with(viewModel) {
            getRemotePokemonsLiveData.observe(this@MainActivity) { result ->
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { pokemons ->
                        pokemons.sortedBy { it.id }
                        writePokemonsToDatabase(pokemons)
                    }
                    .onFailure {
                        showProgress(false)
                        applicationContext.toast(
                            getString(R.string.error_message_getting_data_from_server)
                        )
                    }
            }

            writePokemonsDaoLiveEvent.observe(this@MainActivity) { result ->
                showProgress(false)
                result
                    .onProgress { showProgress(true) }
                    .onSuccess { onDatabaseUpdatedSuccessfully() }
                    .onFailure {
                        applicationContext.toast(
                            getString(R.string.error_message_write_data_to_database)
                        )
                    }
            }
        }
    }
}