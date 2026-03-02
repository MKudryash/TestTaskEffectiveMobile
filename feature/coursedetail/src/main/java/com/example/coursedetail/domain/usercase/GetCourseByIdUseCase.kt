package com.example.coursedetail.domain.usercase


import com.example.coursedetail.domain.model.CourseDetail
import com.example.coursedetail.domain.repository.CourseDetailRepository
import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(
    private val repository: CourseDetailRepository
) {
    suspend operator fun invoke(courseId: Int): CourseDetail {
        return repository.getCourseById(courseId)
    }
}