package com.example.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.example.main.presentation.components.SearchBarWithFilter
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.effectivemobiletesttask.feature.main.presentation.MainViewModel
import com.example.main.domain.MainEffect
import com.example.main.domain.MainEvent
import com.example.main.presentation.components.*

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToCourse: (Int) -> Unit
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    // Обработка эффектов навигации
    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { effect ->
                when (effect) {
                    is MainEffect.NavigateToCourse -> {
                        onNavigateToCourse(effect.courseId)
                    }
                    is MainEffect.ShowError -> {
                        // Здесь можно показать Snackbar
                        println("Error: ${effect.message}")
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SearchBarWithFilter(
            query = state.searchQuery,
            onQueryChange = { viewModel.handleEvent(MainEvent.SearchQueryChanged(it)) },
            onFilterClick = {  }
        )


        SortRow(
            sortAscending = state.sortAscending,
            onSortClick = { viewModel.handleEvent(MainEvent.ToggleSortOrder) },
            colors = colors,
            typography = typography,
            dimensions = dimensions
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primary)
            }
        } else {
            CoursesList(
                courses = state.filteredCourses,
                onCourseClick = { viewModel.handleEvent(MainEvent.CourseClicked(it)) },
                onFavoriteClick = { viewModel.handleEvent(MainEvent.ToggleFavorite(it)) },
                colors = colors,
                dimensions = dimensions,
                typography = typography
            )


        }
    }
}



