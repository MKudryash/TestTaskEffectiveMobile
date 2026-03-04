package com.example.coursedetail.domain.repository

import com.example.domain.model.Course


interface CourseDetailRepository {
    suspend fun getCourseById(courseId: Int): Course
    suspend fun toggleFavorite(courseId: Int)
}