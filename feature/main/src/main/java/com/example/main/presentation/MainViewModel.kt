package com.example.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.MainEffect
import com.example.main.domain.MainEvent
import com.example.main.domain.MainState
import com.example.main.domain.model.Course
import com.example.main.domain.repository.CourseRepository
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
    private val repository: CourseRepository
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
                _state.update { it.copy(searchQuery = event.query) }
                performSearch(event.query)
            }

            MainEvent.ToggleSortOrder -> {
                _state.update {
                    it.copy(
                        sortAscending = !it.sortAscending,
                        filteredCourses = applySort(it.filteredCourses, !it.sortAscending)
                    )
                }
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

    private fun performSearch(query: String) {
        val currentState = _state.value

        val filtered = if (query.isEmpty()) {
            currentState.courses
        } else {
            currentState.courses.filter { course ->
                course.title.contains(query, ignoreCase = true) ||
                        course.description.contains(query, ignoreCase = true)
            }
        }

        _state.update {
            it.copy(
                filteredCourses = applySort(filtered, it.sortAscending)
            )
        }
    }

    private fun applySort(courses: List<Course>, ascending: Boolean): List<Course> {
        return if (ascending) {
            courses.sortedBy { it.publishDate }
        } else {
            courses.sortedByDescending { it.publishDate }
        }
    }

    private fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(courseId)

            _state.update { state ->
                val updatedCourses = state.courses.map { course ->
                    if (course.id == courseId) {
                        course.copy(isFavorite = !course.isFavorite)
                    } else course
                }

                val updatedFiltered = state.filteredCourses.map { course ->
                    if (course.id == courseId) {
                        course.copy(isFavorite = !course.isFavorite)
                    } else course
                }

                state.copy(
                    courses = updatedCourses,
                    filteredCourses = updatedFiltered
                )
            }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val courses = repository.getCourses()

                _state.update {
                    it.copy(
                        courses = courses,
                        filteredCourses = applySort(courses, it.sortAscending),
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