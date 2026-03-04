package com.example.coursedetail.domain.usecase


import com.example.coursedetail.domain.repository.CourseDetailRepository
import com.example.domain.model.Course
import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(
    private val repository: CourseDetailRepository
) {
    suspend operator fun invoke(courseId: Int): Course {
        return repository.getCourseById(courseId)
    }
}