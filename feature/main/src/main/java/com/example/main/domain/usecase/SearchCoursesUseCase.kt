package com.example.main.domain.usecase

import com.example.main.domain.model.Course
import javax.inject.Inject

class SearchCoursesUseCase @Inject constructor() {
    operator fun invoke(courses: List<Course>, query: String): List<Course> {
        return if (query.isEmpty()) {
            courses
        } else {
            courses.filter { course ->
                course.title.contains(query, ignoreCase = true) ||
                        course.description.contains(query, ignoreCase = true)
            }
        }
    }
}