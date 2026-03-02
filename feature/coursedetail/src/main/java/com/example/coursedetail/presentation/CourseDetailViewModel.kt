package com.example.coursedetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursedetail.domain.model.CourseDetail
import com.example.coursedetail.domain.usercase.GetCourseByIdUseCase
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
class CourseDetailViewModel @Inject constructor(
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId: Int = savedStateHandle["courseId"] ?: 0

    private val _state = MutableStateFlow(CourseDetailState(isLoading = true))
    val state: StateFlow<CourseDetailState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CourseDetailEffect>()
    val effect: SharedFlow<CourseDetailEffect> = _effect.asSharedFlow()

    init {
        loadCourse()
    }

    fun handleEvent(event: CourseDetailEvent) {
        when (event) {
            is CourseDetailEvent.ToggleFavorite -> {
                toggleFavorite()
            }
            CourseDetailEvent.Retry -> {
                loadCourse()
            }
            CourseDetailEvent.EnrollCourse -> {
                enrollCourse()
            }
            CourseDetailEvent.ShareCourse -> {
                shareCourse()
            }
        }
    }

    private fun loadCourse() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val course = getCourseByIdUseCase(courseId)
                _state.update {
                    it.copy(
                        course = course,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ошибка загрузки данных"
                    )
                }
            }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val currentCourse = _state.value.course
            if (currentCourse != null) {

                _state.update {
                    it.copy(
                        course = currentCourse.copy(isFavorite = !currentCourse.isFavorite)
                    )
                }


                courseRepository.toggleFavorite(courseId)


                _effect.emit(
                    CourseDetailEffect.ShowMessage(
                        if (!currentCourse.isFavorite) "Добавлено в избранное"
                        else "Удалено из избранного"
                    )
                )
            }
        }
    }

    private fun enrollCourse() {
        viewModelScope.launch {
            _effect.emit(CourseDetailEffect.ShowMessage("Вы записаны на курс!"))
        }
    }

    private fun shareCourse() {
        viewModelScope.launch {
            _effect.emit(CourseDetailEffect.ShareCourse(
                title = _state.value.course?.title ?: "Курс",
                id = courseId
            ))
        }
    }
}

data class CourseDetailState(
    val course: CourseDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class CourseDetailEvent {
    object ToggleFavorite : CourseDetailEvent()
    object Retry : CourseDetailEvent()
    object EnrollCourse : CourseDetailEvent()
    object ShareCourse : CourseDetailEvent()
}

sealed class CourseDetailEffect {
    data class ShowMessage(val message: String) : CourseDetailEffect()
    data class ShareCourse(val title: String, val id: Int) : CourseDetailEffect()
}