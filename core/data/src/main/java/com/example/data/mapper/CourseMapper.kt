package com.example.data.mapper

import com.example.database.model.FavoriteCourseEntity
import com.example.domain.model.Course
import com.example.network.model.CourseDto
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CourseMapper @Inject constructor() {

    fun mapFromDto(dto: CourseDto): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            description = dto.text,
            price = parsePrice(dto.price),
            priceString = dto.price,
            isFavorite = dto.hasLike,
            rating = dto.rate.toDoubleOrNull(),
            startDate = dto.startDate,
            publishDate = dto.publishDate,
            author = "Merion Academy",
            authorImageUrl = null
        )
    }

    fun mapFromEntity(entity: FavoriteCourseEntity): Course {
        return Course(
            id = entity.courseId,
            title = entity.title,
            description = entity.description,
            price = entity.price,
            priceString = entity.priceString,
            currency = entity.currency,
            imageUrl = entity.imageUrl,
            isFavorite = true,
            rating = entity.rating,
            startDate = entity.startDate,
            publishDate = entity.publishDate,
            author = "Merion Academy",
            authorImageUrl = null
        )
    }

    fun mapToEntity(course: Course, userId: String): FavoriteCourseEntity {
        return FavoriteCourseEntity(
            courseId = course.id,
            userId = userId,
            title = course.title,
            description = course.description,
            imageUrl = course.imageUrl,
            publishDate = course.publishDate,
            rating = course.rating,
            startDate = course.startDate,
            price = course.price,
            currency = course.currency,
            priceString = course.priceString
        )
    }

    private fun parsePrice(priceStr: String): Int {
        return priceStr.replace(" ", "").toIntOrNull() ?: 0
    }
}