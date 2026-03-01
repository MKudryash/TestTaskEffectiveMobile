package com.example.main.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val currency: String = "₽",
    val imageUrl: String? = null,
    var isFavorite: Boolean = false,
    val rating: Double? = null,
    val duration: String? = null,
    val level: String? = null,
    val createdAt: Long = System.currentTimeMillis() // Используем Long для timestamp
) {
    fun getFormattedDate(): String {
        val date = Date(createdAt)
        val format = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        return format.format(date)
    }
}