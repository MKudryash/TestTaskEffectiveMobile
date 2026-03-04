package com.example.coursedetail.data.repository

import com.example.coursedetail.domain.repository.CourseDetailRepository
import com.example.data.mapper.CourseMapper
import com.example.database.dao.FavoriteCourseDao
import com.example.domain.model.Course
import com.example.network.api.CourseApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseDetailRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val mapper: CourseMapper,
    private val favoriteDao: FavoriteCourseDao
) : CourseDetailRepository {

    private val coursesCache = MutableStateFlow<Map<Int, Course>>(emptyMap())
    private val currentUserId = "default_user"

    override suspend fun getCourseById(courseId: Int): Course {
        val cachedCourse = coursesCache.value[courseId]
        if (cachedCourse != null) {
            return cachedCourse
        }

        try {
            val response = api.getCourses()
            val favoriteIds = favoriteDao.getFavoriteIdsByUser(currentUserId)

            val courses = response.courses.associateBy(
                { it.id },
                { course ->
                    mapper.mapFromDto(course).copy(
                        isFavorite = course.id in favoriteIds
                    )
                }
            )
            coursesCache.value = courses
            return courses[courseId] ?: throw Exception("Course not found")
        } catch (e: Exception) {
            return getCourseFromLocalOrMock(courseId)
        }
    }

    private suspend fun getCourseFromLocalOrMock(courseId: Int): Course {
        try {
            val favorites = favoriteDao.getFavoritesByUserSync(currentUserId)
            val favoriteCourse = favorites.find { it.courseId == courseId }
            if (favoriteCourse != null) {
                return mapper.mapFromEntity(favoriteCourse)
            }
        } catch (dbError: Exception) {
            // Ignore DB error
        }

        return getMockCourseById(courseId)
    }

    override suspend fun toggleFavorite(courseId: Int) {
        coursesCache.update { cache ->
            cache.toMutableMap().apply {
                val course = this[courseId]
                if (course != null) {
                    val updatedCourse = course.copy(isFavorite = !course.isFavorite)
                    this[courseId] = updatedCourse

                    if (updatedCourse.isFavorite) {
                        val entity = mapper.mapToEntity(updatedCourse, currentUserId)
                        favoriteDao.insertFavorite(entity)
                    } else {
                        favoriteDao.deleteFavoriteByCourseId(courseId, currentUserId)
                    }
                }
            }
        }
    }

    private fun getMockCourseById(courseId: Int): Course {
        val mockCourses = listOf(
            Course(
                id = 100,
                title = "Java-разработчик с нуля",
                description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                price = 999,
                priceString = "999",
                isFavorite = false,
                rating = 4.9,
                startDate = "2024-05-22",
                publishDate = "2024-02-02",
                imageUrl = null,
                currency = "₽",
                author = "Merion Academy"
            ),
            Course(
                id = 101,
                title = "3D-дженералист",
                description = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                price = 12000,
                priceString = "12 000",
                isFavorite = false,
                rating = 3.9,
                startDate = "2024-09-10",
                publishDate = "2024-01-20",
                imageUrl = null,
                currency = "₽",
                author = "Merion Academy"
            ),
            Course(
                id = 102,
                title = "Python Advanced. Для продвинутых",
                description = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Python. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                price = 1299,
                priceString = "1 299",
                isFavorite = true,
                rating = 4.3,
                startDate = "2024-10-12",
                publishDate = "2024-08-10",
                imageUrl = null,
                currency = "₽",
                author = "Merion Academy"
            )
        )

        return mockCourses.find { it.id == courseId } ?: mockCourses[0]
    }
}