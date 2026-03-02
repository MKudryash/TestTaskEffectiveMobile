package com.example.main.presentation

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
import com.example.design.theme.AppColors
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.main.domain.MainEffect
import com.example.main.domain.MainEvent
import com.example.main.presentation.components.CoursesList
import com.example.main.presentation.components.ErrorContent
import com.example.main.presentation.components.LoadingContent
import com.example.main.presentation.components.SearchBarWithFilter
import com.example.main.presentation.components.SortRow

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
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
                    is MainEffect.NavigateToCourse -> {
                        onNavigateToCourse(effect.courseId)
                    }
                    is MainEffect.ShowError -> {
                        println("Error: ${effect.message}")
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, start = dimensions.spacingLarge, end = dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBarWithFilter(
            query = state.searchQuery,
            onQueryChange = { viewModel.handleEvent(MainEvent.SearchQueryChanged(it)) },
            onFilterClick = { }
        )

        SortRow(
            sortAscending = state.sortAscending,
            onSortClick = { viewModel.handleEvent(MainEvent.ToggleSortOrder) },
            colors = colors,
            dimensions = dimensions,
            typography = typography
        )

        if (state.isLoading) {
            LoadingContent()
        } else if (state.error != null) {
            ErrorContent(state.error ?: "Ошибка") { }
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