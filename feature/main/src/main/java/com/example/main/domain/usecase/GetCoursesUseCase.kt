package com.example.main.domain.usecase


import com.example.domain.model.Course
import com.example.main.domain.repository.CourseRepository
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): List<Course> {
        return repository.getCourses()
    }
}