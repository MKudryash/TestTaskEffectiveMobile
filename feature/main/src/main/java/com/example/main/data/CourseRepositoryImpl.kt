package com.example.main.data.repository

import com.example.main.data.mapper.CourseMapper
import com.example.main.domain.model.Course
import com.example.main.domain.repository.CourseRepository
import com.example.network.api.CourseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val mapper: CourseMapper
) : CourseRepository {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): List<Course> {
        try {
            val response = api.getCourses()
            val courses = response.courses.map { mapper.mapToDomain(it) }
            _courses.value = courses
            return courses
        } catch (e: Exception) {
            return _courses.value
        }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        _courses.value = _courses.value.map { course ->
            if (course.id == courseId) {
                course.copy(isFavorite = !course.isFavorite)
            } else course
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return _courses.asStateFlow().map { courses ->
            courses.filter { it.isFavorite }
        }
    }
}