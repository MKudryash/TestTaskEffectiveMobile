package com.example.main.di

import com.example.data.mapper.CourseMapper
import com.example.database.dao.FavoriteCourseDao
import com.example.main.data.CourseRepositoryImpl
import com.example.main.domain.repository.CourseRepository
import com.example.network.api.CourseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideCourseRepository(
        api: CourseApi,
        mapper: CourseMapper,
        favoriteDao: FavoriteCourseDao
    ): CourseRepository {
        return CourseRepositoryImpl(api, mapper, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideCourseMapper(): CourseMapper {
        return CourseMapper()
    }
}