package com.example.main.data.mapper

import com.example.main.domain.model.Course
import com.example.network.model.CourseDto
import javax.inject.Inject

class CourseMapper @Inject constructor() {

    fun mapToDomain(dto: CourseDto): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            description = dto.text,
            price = parsePrice(dto.price),
            priceString = dto.price,
            isFavorite = dto.hasLike,
            rating = dto.rate.toDoubleOrNull(),
            startDate = dto.startDate,
            publishDate = dto.publishDate
        )
    }

    private fun parsePrice(priceStr: String): Int {
        return priceStr.replace(" ", "").toIntOrNull() ?: 0
    }
}