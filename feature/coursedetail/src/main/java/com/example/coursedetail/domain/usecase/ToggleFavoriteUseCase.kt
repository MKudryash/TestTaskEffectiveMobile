
package com.example.coursedetail.domain.usecase

import com.example.coursedetail.domain.repository.CourseDetailRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CourseDetailRepository
) {
    suspend operator fun invoke(courseId: Int) {
        repository.toggleFavorite(courseId)
    }
}