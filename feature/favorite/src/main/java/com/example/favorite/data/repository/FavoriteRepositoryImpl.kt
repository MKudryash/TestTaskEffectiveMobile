package com.example.favorite.data.repository

import com.example.database.dao.FavoriteCourseDao
import com.example.database.model.FavoriteCourseEntity
import com.example.domain.model.Course
import com.example.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCourseDao: FavoriteCourseDao
) : FavoriteRepository {

    private val currentUserId = "default_user"

    override fun getFavorites(): Flow<List<Course>> {
        return favoriteCourseDao.getFavoritesByUser(currentUserId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun removeFromFavorite(courseId: Int) {
        favoriteCourseDao.deleteFavoriteByCourseId(courseId, currentUserId)
    }

    private fun FavoriteCourseEntity.toDomain(): Course {
        return Course(
            id = courseId,
            title = title,
            description = description,
            imageUrl = imageUrl,
            publishDate = publishDate,
            isFavorite = true,
            price = price,
            priceString = priceString,
            startDate = startDate,
            currency = currency,
            rating = rating
        )
    }
}