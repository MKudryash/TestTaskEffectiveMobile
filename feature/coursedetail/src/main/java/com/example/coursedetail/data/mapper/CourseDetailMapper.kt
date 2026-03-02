package com.example.coursedetail.data.mapper

import com.example.coursedetail.domain.model.CourseDetail
import com.example.network.model.CourseDto
import javax.inject.Inject

class CourseDetailMapper @Inject constructor() {

    fun mapToDomain(dto: CourseDto): CourseDetail {
        return CourseDetail(
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