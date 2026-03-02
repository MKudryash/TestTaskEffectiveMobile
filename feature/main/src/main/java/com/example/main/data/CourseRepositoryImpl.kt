package com.example.main.data.repository


import com.example.database.dao.FavoriteCourseDao
import com.example.database.model.FavoriteCourseEntity
import com.example.main.data.mapper.CourseMapper
import com.example.main.domain.model.Course
import com.example.main.domain.repository.CourseRepository
import com.example.network.api.CourseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val mapper: CourseMapper,
    private val favoriteDao: FavoriteCourseDao
) : CourseRepository {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())

    private val currentUserId = "default_user"

    override suspend fun getCourses(): List<Course> {
        try {
            val response = api.getCourses()
            val courses = response.courses.map { mapper.mapToDomain(it) }


            val favoriteIds = favoriteDao.getFavoriteIdsByUser(currentUserId)


            val coursesWithFavorites = courses.map { course ->
                course.copy(isFavorite = course.id in favoriteIds)
            }

            _courses.value = coursesWithFavorites
            return coursesWithFavorites
        } catch (e: Exception) {

            return _courses.value
        }
    }

    override suspend fun toggleFavorite(courseId: Int) {

        val course = _courses.value.find { it.id == courseId } ?: return


        _courses.update { courses ->
            courses.map { c ->
                if (c.id == courseId) {
                    c.copy(isFavorite = !c.isFavorite)
                } else c
            }
        }


        if (course.isFavorite) {

            favoriteDao.deleteFavoriteByCourseId(courseId, currentUserId)
        } else {

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
            favoriteDao.insertFavorite(entity)
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {

        return combine(
            favoriteDao.getFavoritesByUser(currentUserId),
            flowOf(_courses.value)
        ) { favorites, allCourses ->
            if (allCourses.isNotEmpty()) {

                val favoriteIds = favorites.map { it.courseId }.toSet()
                allCourses.filter { it.id in favoriteIds }
            } else {

                favorites.map { entity ->
                    Course(
                        id = entity.courseId,
                        title = entity.title,
                        description = entity.description,
                        imageUrl = entity.imageUrl,
                        publishDate = entity.publishDate,
                        isFavorite = true,
                        rating = entity.rating,
                        price = entity.price,
                        currency = entity.currency,
                        startDate = entity.startDate,
                        priceString = entity.priceString
                    )
                }
            }
        }
    }
}