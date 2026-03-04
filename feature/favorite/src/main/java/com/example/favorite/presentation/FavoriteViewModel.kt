package com.example.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Course
import com.example.favorite.domain.usecase.GetFavoritesUseCase
import com.example.favorite.domain.usecase.RemoveFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState(isLoading = true))
    val state: StateFlow<FavoriteState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FavoriteEffect>()
    val effect: SharedFlow<FavoriteEffect> = _effect.asSharedFlow()

    init {
        loadFavorites()
    }

    fun handleEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.CourseClicked -> {
                viewModelScope.launch {
                    _effect.emit(FavoriteEffect.NavigateToCourse(event.courseId))
                }
            }
            is FavoriteEvent.RemoveFromFavorite -> {
                removeFromFavorite(event.courseId)
            }
            FavoriteEvent.Refresh -> {
                loadFavorites()
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase()
                .onStart {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Ошибка загрузки избранного: ${e.message}"
                        )
                    }
                    _effect.emit(FavoriteEffect.ShowError("Не удалось загрузить избранное"))
                }
                .collect { favorites ->
                    _state.update {
                        it.copy(
                            favoriteCourses = favorites,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun removeFromFavorite(courseId: Int) {
        viewModelScope.launch {
            try {
                removeFromFavoriteUseCase(courseId)
            } catch (e: Exception) {
                _effect.emit(FavoriteEffect.ShowError("Ошибка при удалении из избранного"))
            }
        }
    }
}

data class FavoriteState(
    val favoriteCourses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class FavoriteEvent {
    data class CourseClicked(val courseId: Int) : FavoriteEvent()
    data class RemoveFromFavorite(val courseId: Int) : FavoriteEvent()
    object Refresh : FavoriteEvent()
}

sealed class FavoriteEffect {
    data class NavigateToCourse(val courseId: Int) : FavoriteEffect()
    data class ShowError(val message: String) : FavoriteEffect()
}