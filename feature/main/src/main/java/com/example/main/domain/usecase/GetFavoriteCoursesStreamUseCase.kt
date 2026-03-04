package com.example.main.domain.usecase

import com.example.domain.model.Course
import com.example.main.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCoursesStreamUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getFavoriteCourses()
    }
}