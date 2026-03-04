package com.example.main.domain.usecase

import com.example.main.domain.model.Course
import javax.inject.Inject

class SortCoursesUseCase @Inject constructor() {
    operator fun invoke(courses: List<Course>, ascending: Boolean): List<Course> {
        return if (ascending) {
            courses.sortedBy { it.publishDate }
        } else {
            courses.sortedByDescending { it.publishDate }
        }
    }
}