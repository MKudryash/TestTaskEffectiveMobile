package com.example.coursedetail.domain.model

import java.text.SimpleDateFormat
import java.util.Locale

data class CourseDetail(
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
    val author:String? = "Merion Academy",
    val imageAuthorUrl: String? = null
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

    fun getFormattedStartDate(): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = format.parse(startDate)
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            date?.let { outputFormat.format(it) } ?: startDate
        } catch (e: Exception) {
            startDate
        }
    }

    fun getDisplayPrice(): String {
        return "$priceString $currency"
    }
}