package com.example.main.domain

import com.example.main.domain.model.Course

sealed class MainEvent {
    data class SearchQueryChanged(val query: String) : MainEvent()
    object ToggleSortOrder : MainEvent()
    data class CourseClicked(val courseId: Int) : MainEvent()
    data class ToggleFavorite(val courseId: Int) : MainEvent()
    object RefreshCourses : MainEvent()
}

data class MainState(
    val courses: List<Course> = emptyList(),
    val filteredCourses: List<Course> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val sortAscending: Boolean = true
)

sealed class MainEffect {
    data class NavigateToCourse(val courseId: Int) : MainEffect()
    data class ShowError(val message: String) : MainEffect()
}