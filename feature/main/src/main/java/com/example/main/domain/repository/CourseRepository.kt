package com.example.main.domain.repository

import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourses(): List<Course>
    suspend fun toggleFavorite(courseId: Int)
    fun getFavoriteCourses(): Flow<List<Course>>
}