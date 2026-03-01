package com.example.auth.di

import com.example.auth.domain.manager.ExternalNavigationManager
import com.example.auth.domain.manager.ExternalNavigationManagerImpl
import com.example.auth.domain.usecase.OpenBrowserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideOpenBrowserUseCase(): OpenBrowserUseCase {
        return OpenBrowserUseCase()
    }

    @Provides
    @Singleton
    fun provideExternalNavigationManager(): ExternalNavigationManager {
        return ExternalNavigationManagerImpl()
    }
}