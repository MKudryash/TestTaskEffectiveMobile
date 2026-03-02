package com.example.favorite.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.favorite.presentation.components.ErrorContent
import com.example.main.presentation.components.CoursesList

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigateToCourse: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { effect ->
                when (effect) {
                    is FavoriteEffect.NavigateToCourse -> {
                        onNavigateToCourse(effect.courseId)
                    }
                    is FavoriteEffect.ShowError -> {
                        println("Error: ${effect.message}")
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, start = dimensions.spacingLarge, end = dimensions.spacingLarge)
    ) {
        Text(
            text = "Избранное",
            style = typography.displayMedium,
            color = colors.textPrimary,
            modifier = Modifier.padding(bottom = dimensions.spacingLarge)
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            ErrorContent(error = state.error ?: "Ошибка загрузки", {})

        } else if (state.favoriteCourses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "У вас пока нет избранных курсов",
                    style = typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
        } else {
            CoursesList(
                courses = state.favoriteCourses,
                onCourseClick = { viewModel.handleEvent(FavoriteEvent.CourseClicked(it)) },
                onFavoriteClick = { viewModel.handleEvent(FavoriteEvent.RemoveFromFavorite(it)) },
                colors = colors,
                dimensions = dimensions,
                typography = typography
            )
        }
    }
}