package com.example.network.di

import com.example.domain.repository.UrlOpenerRepository
import com.example.network.util.UrlOpenerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindUrlOpenerRepository(
        impl: UrlOpenerRepositoryImpl
    ): UrlOpenerRepository
}