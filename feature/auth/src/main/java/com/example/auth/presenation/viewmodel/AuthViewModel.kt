package com.example.auth.presenation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.presenation.states.AuthState
import com.example.domain.usecase.OpenUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val openUrlUseCase: OpenUrlUseCase
) : ViewModel() {

    private val _urlOpenResult = MutableSharedFlow<UrlOpenResult>()
    val urlOpenResult: SharedFlow<UrlOpenResult> = _urlOpenResult

    fun openUrl(url: String) {
        viewModelScope.launch {
            val result = openUrlUseCase(url)
            result.onSuccess {
                _urlOpenResult.emit(UrlOpenResult.Success)
            }.onFailure { exception ->
                _urlOpenResult.emit(UrlOpenResult.Error(exception.message ?: "Ошибка открытия ссылки"))
            }
        }
    }
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            delay(1000)

            if (email.isNotBlank() && password.isNotBlank()) {
                _authState.value = AuthState.Success
                Log.d("AuthViewModel", "Success")
            } else {
                _authState.value = AuthState.Error("Неверный email или пароль")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class UrlOpenResult {
    object Success : UrlOpenResult()
    data class Error(val message: String) : UrlOpenResult()
}