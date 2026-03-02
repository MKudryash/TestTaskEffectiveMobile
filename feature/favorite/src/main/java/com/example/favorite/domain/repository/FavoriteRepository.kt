package com.example.favorite.domain.repository


import com.example.database.dao.FavoriteCourseDao
import com.example.database.model.FavoriteCourseEntity
import com.example.main.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteCourseDao: FavoriteCourseDao
) {

    private val currentUserId = "default_user"

    fun getFavorites(): Flow<List<Course>> {
        return favoriteCourseDao.getFavoritesByUser(currentUserId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getFavoriteIds(): List<Int> {
        return favoriteCourseDao.getFavoriteIdsByUser(currentUserId)
    }

    suspend fun addToFavorite(course: Course) {
        val entity = FavoriteCourseEntity(
            courseId = course.id,
            userId = currentUserId,
            title = course.title,
            description = course.description,
            imageUrl = course.imageUrl,
            publishDate = course.publishDate,
            priceString =  course.priceString,
            currency = course.currency,
            startDate = course.startDate,
            price = course.price,
            rating = course.rating
        )
        favoriteCourseDao.insertFavorite(entity)
    }

    suspend fun removeFromFavorite(courseId: Int) {
        favoriteCourseDao.deleteFavoriteByCourseId(courseId, currentUserId)
    }

    suspend fun isFavorite(courseId: Int): Boolean {
        return favoriteCourseDao.isFavorite(courseId, currentUserId)
    }

    suspend fun toggleFavorite(course: Course) {
        if (isFavorite(course.id)) {
            removeFromFavorite(course.id)
        } else {
            addToFavorite(course)
        }
    }

    private fun FavoriteCourseEntity.toDomain(): Course {
        return Course(
            id = courseId,
            title = title,
            description = description,
            imageUrl = imageUrl,
            publishDate = publishDate,
            isFavorite = true ,
            price = price,
            priceString = priceString,
            startDate = startDate,
            currency = currency,
            rating = rating
        )
    }
}
