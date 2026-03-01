package com.example.effectivemobiletesttask.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.MainEffect
import com.example.main.domain.MainEvent
import com.example.main.domain.MainState
import com.example.main.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainState(isLoading = true))
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MainEffect>()
    val effect: SharedFlow<MainEffect> = _effect.asSharedFlow()

    // Заглушка с данными с разными датами
    private val mockCourses = listOf(
        Course(
            id = 1,
            title = "Java-разработчик с нуля",
            description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
            price = 999,
            level = "Начальный",
            duration = "12 месяцев",
            rating = 4.8,
            createdAt = getTimestamp(2025, 5, 22) // 22 мая 2025
        ),
        Course(
            id = 2,
            title = "Kotlin для Android разработчиков",
            description = "Изучите современную разработку под Android на Kotlin. Coroutines, Jetpack Compose, MVVM, Clean Architecture и многое другое.",
            price = 1299,
            level = "Средний",
            duration = "8 месяцев",
            rating = 4.9,
            createdAt = getTimestamp(2025, 4, 15) // 15 апреля 2025
        ),
        Course(
            id = 3,
            title = "Python разработчик",
            description = "Научитесь создавать веб-приложения на Django и Flask, работать с данными и автоматизировать задачи. Станьте востребованным Python разработчиком.",
            price = 899,
            level = "Начальный",
            duration = "10 месяцев",
            rating = 4.7,
            createdAt = getTimestamp(2025, 3, 10) // 10 марта 2025
        ),
        Course(
            id = 4,
            title = "Frontend разработчик",
            description = "Освойте HTML, CSS, JavaScript, React и современные фреймворки. Научитесь создавать адаптивные и интерактивные веб-интерфейсы.",
            price = 1099,
            level = "Начальный",
            duration = "9 месяцев",
            rating = 4.6,
            createdAt = getTimestamp(2025, 2, 5) // 5 февраля 2025
        ),
        Course(
            id = 5,
            title = "iOS разработчик на Swift",
            description = "Изучите разработку приложений для iPhone и iPad на Swift. SwiftUI, UIKit, CoreData, работа с сетью и публикация в App Store.",
            price = 1399,
            level = "Средний",
            duration = "11 месяцев",
            rating = 4.8,
            createdAt = getTimestamp(2025, 1, 20) // 20 января 2025
        ),
        Course(
            id = 6,
            title = "Data Scientist",
            description = "Освойте машинное обучение, анализ данных и нейронные сети. Python, Pandas, Scikit-learn, TensorFlow и подготовка данных.",
            price = 1599,
            level = "Продвинутый",
            duration = "14 месяцев",
            rating = 4.9,
            createdAt = getTimestamp(2024, 12, 1) // 1 декабря 2024
        )
    )

    init {
        loadMockCourses()
    }

    private fun loadMockCourses() {
        viewModelScope.launch {
            // Имитация загрузки
            delay(1000)

            _state.update {
                it.copy(
                    courses = mockCourses,
                    filteredCourses = mockCourses,
                    isLoading = false
                )
            }
        }
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
                refreshCourses()
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
                        course.description.contains(query, ignoreCase = true) ||
                        course.level?.contains(query, ignoreCase = true) == true
            }
        }

        _state.update {
            it.copy(
                filteredCourses = applySort(filtered, it.sortAscending)
            )
        }
    }

    private fun applySort(courses: List<Course>, ascending: Boolean): List<Course> {
        // Сортируем по дате создания (createdAt)
        return if (ascending) {
            courses.sortedBy { it.createdAt }
        } else {
            courses.sortedByDescending { it.createdAt }
        }
    }

    private fun toggleFavorite(courseId: Int) {
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

    private fun refreshCourses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(1000)

            val shuffled = _state.value.courses.shuffled()

            _state.update {
                it.copy(
                    courses = shuffled,
                    filteredCourses = applySort(shuffled, it.sortAscending),
                    isLoading = false
                )
            }
        }
    }

    // Вспомогательная функция для создания timestamp из даты
    private fun getTimestamp(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, 0, 0, 0)
        return calendar.timeInMillis
    }
}