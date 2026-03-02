package com.example.coursedetail.di

import com.example.coursedetail.data.mapper.CourseDetailMapper
import com.example.coursedetail.data.repository.CourseDetailRepositoryImpl
import com.example.coursedetail.domain.repository.CourseDetailRepository
import com.example.coursedetail.domain.usercase.GetCourseByIdUseCase
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
        repositoryImpl: CourseDetailRepositoryImpl
    ): CourseDetailRepository {
        return repositoryImpl
    }

    @Provides
    @Singleton
    fun provideGetCourseByIdUseCase(
        repository: CourseDetailRepository
    ): GetCourseByIdUseCase {
        return GetCourseByIdUseCase(repository)
    }
}