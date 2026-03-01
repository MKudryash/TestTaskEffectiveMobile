package com.example.main.di

import com.example.main.data.CourseRepositoryImpl
import com.example.main.domain.repository.CourseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    @Singleton
    abstract fun bindCourseRepository(
        repositoryImpl: CourseRepositoryImpl
    ): CourseRepository
}