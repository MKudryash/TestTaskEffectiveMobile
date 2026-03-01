package com.example.main.data

import com.example.main.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
   /* private val courseApi: CourseApi,
    private val courseDao: CourseDao*/
) : CourseRepository {

   /* override fun getCourses(): Flow<List<Course>> {
        return courseDao.getAllCourses()
            .map { entities -> entities.map { it.toDomain() } }
            .onStart {
                // Если база пустая, загружаем с сервера
                val courses = courseDao.getAllCourses().first()
                if (courses.isEmpty()) {
                    refreshCourses()
                }
            }
    }

    override suspend fun getCourseById(id: Int): Course? {
        // Сначала проверяем в БД
        val localCourse = courseDao.getCourseById(id)?.toDomain()
        if (localCourse != null) return localCourse

        // Если нет в БД, грузим с сервера
        return try {
            val remoteCourse = courseApi.getCourseById(id)
            val course = remoteCourse.toDomain()
            // Сохраняем в БД
            courseDao.insertAll(listOf(course.toEntity()))
            course
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        val course = courseDao.getCourseById(courseId)
        course?.let {
            val newFavoriteStatus = !it.isFavorite
            courseDao.updateFavoriteStatus(courseId, newFavoriteStatus)
        }
    }

    override suspend fun searchCourses(query: String): List<Course> {
        // Локальный поиск
        val localResults = courseDao.getAllCourses()
            .first()
            .filter { entity ->
                entity.title.contains(query, ignoreCase = true) ||
                        entity.description.contains(query, ignoreCase = true)
            }
            .map { it.toDomain() }

        // Если локальных результатов мало, ищем на сервере
        if (localResults.size < 3) {
            try {
                val remoteResults = courseApi.searchCourses(query)
                // Сохраняем результаты в БД
                val entities = remoteResults.map { dto ->
                    val existing = courseDao.getCourseById(dto.id)
                    dto.toDomain(existing?.isFavorite ?: false).toEntity()
                }
                courseDao.insertAll(entities)

                return remoteResults.map { dto ->
                    val existing = courseDao.getCourseById(dto.id)
                    dto.toDomain(existing?.isFavorite ?: false)
                }
            } catch (e: Exception) {
                // В случае ошибки возвращаем локальные результаты
            }
        }

        return localResults
    }

    override suspend fun refreshCourses() {
        try {
            val remoteCourses = courseApi.getCourses()

            // Получаем текущие избранные статусы
            val currentFavorites = courseDao.getAllCourses()
                .first()
                .associate { it.id to it.isFavorite }

            // Обновляем данные, сохраняя избранное
            val entities = remoteCourses.map { dto ->
                dto.toDomain(currentFavorites[dto.id] ?: false).toEntity()
            }

            courseDao.insertAll(entities)
        } catch (e: Exception) {
            // Обработка ошибки
            throw e
        }
    }*/
}