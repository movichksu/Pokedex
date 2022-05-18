package com.pahomovichk.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import com.pahomovichk.pokedex.core.ui.FlowRouter
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    private val appCicerone: Cicerone<Router> = Cicerone.create()
    private val flowCicerone: Cicerone<FlowRouter> = Cicerone.create(FlowRouter(appCicerone.router))

    @Provides
    @Singleton
    fun provideAppNavigator(): NavigatorHolder = appCicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideAppRouter(): Router = appCicerone.router

    @Provides
    @Singleton
    @FlowRouterQualifier
    fun provideFlowNavigator(): NavigatorHolder = flowCicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideFlowRouter(): FlowRouter = flowCicerone.router
}