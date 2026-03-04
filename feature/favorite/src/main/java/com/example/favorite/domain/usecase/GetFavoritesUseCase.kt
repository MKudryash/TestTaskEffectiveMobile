package com.example.favorite.domain.usecase

import com.example.domain.model.Course
import com.example.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getFavorites()
    }
}