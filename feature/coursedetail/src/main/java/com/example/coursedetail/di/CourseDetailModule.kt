package com.example.coursedetail.di

import com.example.coursedetail.data.mapper.CourseDetailMapper
import com.example.coursedetail.data.repository.CourseDetailRepositoryImpl
import com.example.coursedetail.domain.repository.CourseDetailRepository
import com.example.coursedetail.domain.usecase.GetCourseByIdUseCase
import com.example.database.dao.FavoriteCourseDao
import com.example.network.api.CourseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CourseDetailModule {

    @Provides
    @Singleton
    fun provideCourseDetailMapper(): CourseDetailMapper {
        return CourseDetailMapper()
    }

    @Provides
    @Singleton
    fun provideCourseDetailRepository(
        api: CourseApi,
        mapper: CourseDetailMapper,
        favoriteDao: FavoriteCourseDao
    ): CourseDetailRepository {
        return CourseDetailRepositoryImpl(api, mapper, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideGetCourseByIdUseCase(
        repository: CourseDetailRepository
    ): GetCourseByIdUseCase {
        return GetCourseByIdUseCase(repository)
    }
}