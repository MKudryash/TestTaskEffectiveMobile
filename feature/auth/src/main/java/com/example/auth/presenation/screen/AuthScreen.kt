package com.example.auth.presenation.screen

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.presenation.components.OtherAuthForm
import com.example.auth.presenation.states.AuthState
import com.example.auth.presenation.viewmodel.AuthViewModel
import com.example.design.components.button.AppButton
import com.example.design.components.feedback.ErrorMessage
import com.example.design.components.input.AppTextField
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {

    val dimensions = LocalAppDimensions.current


    var isLoginMode by remember { mutableStateOf(true) }


    val snackbarHostState = remember { SnackbarHostState() }


    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                onAuthSuccess()
            }
            is AuthState.Error -> {
                snackbarHostState.showSnackbar(
                    message = (authState as AuthState.Error).message
                )
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensions.spacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (authState is AuthState.Loading) {
                CircularProgressIndicator()
            } else {

                AuthContent(
                    isLoginMode = isLoginMode,
                    onLoginClick = { email, password ->
                        viewModel.login(email, password)
                    },
                    onRegisterClick = { email, password, name ->

                    },
                    onToggleMode = { isLoginMode = !isLoginMode }
                )
            }
        }
    }
}

@Composable
fun AuthContent(
    isLoginMode: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: (String, String, String) -> Unit,
    onToggleMode: () -> Unit
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensions.spacingLarge)
        ) {
            Text(
                text = "Вход",
                color = colors.textPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                style = typography.displayMedium,
                textAlign = TextAlign.Start
            )


            AppTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it)
                },
                label = "Email",
                placeholder = "example@gmail.com",
                isEmail = true,
                modifier = Modifier.Companion.fillMaxWidth()
            )

            if (!isEmailValid && email.isNotEmpty()) {
                ErrorMessage(text = "Введите корректный email адрес")
            }


            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 6
                },
                label = "Пароль",
                placeholder = "Введите пароль",
                isPassword = true,
                modifier = Modifier.Companion.fillMaxWidth()
            )

            if (!isPasswordValid && password.isNotEmpty()) {
                ErrorMessage(text = "Пароль должен содержать минимум 6 символов")
            }


            Spacer(modifier = Modifier.Companion.height(dimensions.spacingMedium))


            AppButton(
                text = "Вход",
                onClick = {
                    if (isValidEmail(email) && password.length >= 6) {
                        onLoginClick(email,password)
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty() && isValidEmail(email) && password.length >= 6
            )
        }
        Spacer(modifier = Modifier.height(dimensions.spacingLarge))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Нету аккаунта? ",
                    color = colors.textPrimary,
                    style = typography.bodySmall
                )

                Text(
                    text = "Регистрация",
                    color = colors.primary,
                    style = typography.bodySmall,
                    modifier = Modifier.clickable {  }
                )
            }

            Text(
                text = "Забыл пароль",
                color = colors.primary,
                style = typography.bodySmall,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
        Spacer(Modifier.height(dimensions.spacingExtraExtraLarge))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color(0xFF4D555E),
            thickness = 1.dp
        )
        Spacer(Modifier.height(dimensions.spacingExtraExtraLarge))

        OtherAuthForm(snackbarHostState = snackbarHostState)
    }
}
fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}