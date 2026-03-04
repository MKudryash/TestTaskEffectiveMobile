// file: presentation/MainViewModel.kt
package com.example.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.MainEffect
import com.example.main.domain.MainEvent
import com.example.main.domain.MainState
import com.example.main.domain.model.Course
import com.example.main.domain.usecase.GetCoursesUseCase
import com.example.main.domain.usecase.SearchCoursesUseCase
import com.example.main.domain.usecase.SortCoursesUseCase
import com.example.main.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val searchCoursesUseCase: SearchCoursesUseCase,
    private val sortCoursesUseCase: SortCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState(isLoading = true))
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MainEffect>()
    val effect: SharedFlow<MainEffect> = _effect.asSharedFlow()

    init {
        handleEvent(MainEvent.LoadCourses)
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchQueryChanged -> {
                updateSearchQuery(event.query)
            }

            MainEvent.ToggleSortOrder -> {
                toggleSortOrder()
            }

            is MainEvent.CourseClicked -> {
                viewModelScope.launch {
                    _effect.emit(MainEffect.NavigateToCourse(event.courseId))
                }
            }

            is MainEvent.ToggleFavorite -> {
                toggleFavorite(event.courseId)
            }

            MainEvent.RefreshCourses -> {
                loadCourses()
            }

            MainEvent.LoadCourses -> {
                loadCourses()
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }

        val currentState = _state.value
        val searchResult = searchCoursesUseCase(currentState.courses, query)
        val sortedResult = sortCoursesUseCase(searchResult, currentState.sortAscending)

        _state.update {
            it.copy(filteredCourses = sortedResult)
        }
    }

    private fun toggleSortOrder() {
        _state.update { state ->
            val newSortAscending = !state.sortAscending
            val sortedCourses = sortCoursesUseCase(state.filteredCourses, newSortAscending)

            state.copy(
                sortAscending = newSortAscending,
                filteredCourses = sortedCourses
            )
        }
    }

    private fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            val currentCourse = _state.value.courses.find { it.id == courseId }

            if (currentCourse != null) {
                updateCourseFavoriteStatus(courseId, !currentCourse.isFavorite)

                try {
                    toggleFavoriteUseCase(courseId)
                } catch (e: Exception) {
                    updateCourseFavoriteStatus(courseId, currentCourse.isFavorite)
                    _effect.emit(MainEffect.ShowError("Ошибка при изменении избранного"))
                }
            }
        }
    }

    private fun updateCourseFavoriteStatus(courseId: Int, isFavorite: Boolean) {
        _state.update { state ->
            val updatedCourses = state.courses.map { course ->
                if (course.id == courseId) {
                    course.copy(isFavorite = isFavorite)
                } else course
            }

            val updatedFiltered = state.filteredCourses.map { course ->
                if (course.id == courseId) {
                    course.copy(isFavorite = isFavorite)
                } else course
            }

            state.copy(
                courses = updatedCourses,
                filteredCourses = updatedFiltered
            )
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val courses = getCoursesUseCase()
                val sortedCourses = sortCoursesUseCase(courses, _state.value.sortAscending)

                _state.update {
                    it.copy(
                        courses = courses,
                        filteredCourses = sortedCourses,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка загрузки данных: ${e.message}"
                    )
                }
                _effect.emit(MainEffect.ShowError("Не удалось загрузить курсы"))
            }
        }
    }
}