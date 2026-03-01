package com.example.auth.presenation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.presenation.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

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

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Имитация запроса к API
            delay(1000)

            if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank()) {
                // Здесь должна быть реальная регистрация
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Заполните все поля")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}