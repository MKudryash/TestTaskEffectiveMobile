package com.example.coursedetail.data.repository

import com.example.coursedetail.data.mapper.CourseDetailMapper
import com.example.coursedetail.domain.model.CourseDetail
import com.example.coursedetail.domain.repository.CourseDetailRepository
import com.example.network.api.CourseApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseDetailRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val mapper: CourseDetailMapper
) : CourseDetailRepository {

    private val coursesCache = MutableStateFlow<Map<Int, CourseDetail>>(emptyMap())

    override suspend fun getCourseById(courseId: Int): CourseDetail {

        val cachedCourse = coursesCache.value[courseId]
        if (cachedCourse != null) {
            return cachedCourse
        }


        try {
            val response = api.getCourses()
            val courses = response.courses.associateBy(
                { it.id },
                { mapper.mapToDomain(it) }
            )
            coursesCache.value = courses
            return courses[courseId] ?: throw Exception("Course not found")
        } catch (e: Exception) {

            return getMockCourseById(courseId)
        }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        coursesCache.update { cache ->
            cache.toMutableMap().apply {
                val course = this[courseId]
                if (course != null) {
                    this[courseId] = course.copy(isFavorite = !course.isFavorite)
                }
            }
        }
    }

    private fun getMockCourseById(courseId: Int): CourseDetail {
        val mockCourses = listOf(
            CourseDetail(
                id = 100,
                title = "Java-разработчик с нуля",
                description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                price = 999,
                priceString = "999",
                isFavorite = false,
                rating = 4.9,
                startDate = "2024-05-22",
                publishDate = "2024-02-02",
            ),
            CourseDetail(
                id = 101,
                title = "3D-дженералист",
                description = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                price = 12000,
                priceString = "12 000",
                isFavorite = false,
                rating = 3.9,
                startDate = "2024-09-10",
                publishDate = "2024-01-20",
            ),
            CourseDetail(
                id = 102,
                title = "Python Advanced. Для продвинутых",
                description = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Python. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                price = 1299,
                priceString = "1 299",
                isFavorite = true,
                rating = 4.3,
                startDate = "2024-10-12",
                publishDate = "2024-08-10",
            )
        )

        return mockCourses.find { it.id == courseId } ?: mockCourses[0]
    }
}