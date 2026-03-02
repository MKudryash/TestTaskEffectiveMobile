package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourseEntity(
    @PrimaryKey
    val courseId: Int,
    val userId: String = "default_user",
    val title: String,
    val description: String,
    val imageUrl: String?,
    val publishDate: String,
    val addedAt: Long = System.currentTimeMillis(),
    val rating: Double? = null,
    val startDate: String,
    val price: Int,
    val currency: String = "₽",
    val priceString: String,
)