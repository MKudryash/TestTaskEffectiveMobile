package com.example.favorite.domain.repository

import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Course>>
    suspend fun removeFromFavorite(courseId: Int)
}