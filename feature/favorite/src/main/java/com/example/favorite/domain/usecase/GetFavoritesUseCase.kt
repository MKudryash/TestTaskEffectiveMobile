package com.example.favorite.domain.usecase

import com.example.favorite.domain.repository.FavoriteRepository
import com.example.main.domain.model.Course
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getFavorites()
    }
}