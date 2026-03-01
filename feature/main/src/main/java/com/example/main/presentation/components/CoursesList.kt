package com.example.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.design.components.card.CourseCard
import com.example.design.theme.AppColors
import com.example.design.theme.AppDimensions
import com.example.design.theme.AppTypography
import com.example.main.domain.model.Course

@Composable
fun CoursesList(
    courses: List<Course>,
    onCourseClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    colors: AppColors,
    dimensions: AppDimensions,
    typography: AppTypography
) {
    if (courses.isEmpty()) {
        Box(
            modifier = Modifier.Companion.fillMaxSize(),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text(
                text = "Курсы не найдены",
                style = typography.bodyMedium,
                color = colors.textSecondary
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensions.spacingLarge)
        ) {
            items(courses, key = { it.id }) { course ->
                CourseCard(
                    title = course.title,
                    description = course.description,
                    price = course.price,
                    rating = course.rating,
                    date = course.getFormattedDate(),
                    isFavorite = course.isFavorite,
                    onFavoriteClick = { course.isFavorite = !course.isFavorite },
                    onCardClick = {},
                    imageUrl = course.imageUrl
                )
            }
        }
    }
}