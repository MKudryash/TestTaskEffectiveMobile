package com.example.domain.model

import java.text.SimpleDateFormat
import java.util.Locale

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val priceString: String,
    val currency: String = "₽",
    val imageUrl: String? = null,
    val isFavorite: Boolean = false,
    val rating: Double? = null,
    val startDate: String,
    val publishDate: String,
    val author: String? = "Merion Academy",
    val authorImageUrl: String? = null
) {
    fun getFormattedPublishDate(): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = format.parse(publishDate)
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            date?.let { outputFormat.format(it) } ?: publishDate
        } catch (e: Exception) {
            publishDate
        }
    }

    fun toCourseDetail(): Course = this
}