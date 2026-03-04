package com.example.favorite.domain.usecase

import com.example.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(courseId: Int) {
        repository.removeFromFavorite(courseId)
    }
}