package com.pahomovichk.pokedex.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CacheDirectory

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowRouterQualifier