package com.example.coursedetail.domain.repository

import com.example.coursedetail.domain.model.CourseDetail

interface CourseDetailRepository {
    suspend fun getCourseById(courseId: Int): CourseDetail
    suspend fun toggleFavorite(courseId: Int)
}