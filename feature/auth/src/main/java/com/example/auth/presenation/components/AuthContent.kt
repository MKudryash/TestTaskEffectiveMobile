package com.example.auth.presenation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.auth.presenation.screen.isValidEmail
import com.example.auth.presenation.viewmodel.AuthViewModel
import com.example.design.components.button.AppButton
import com.example.design.R
import com.example.design.components.feedback.ErrorMessage
import com.example.design.components.input.AppTextField
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography

@Composable
fun AuthContent(
    isLoginMode: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: (String, String, String) -> Unit,
    onToggleMode: () -> Unit,
    viewModel: AuthViewModel
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
        modifier = Modifier.Companion.fillMaxWidth(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.Companion.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensions.spacingLarge)
        ) {
            Text(
                text = stringResource(R.string.signInBtn),
                color = colors.textPrimary,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                style = typography.displayMedium,
                textAlign = TextAlign.Companion.Start
            )


            AppTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it)
                },
                label = stringResource(R.string.labelEmail),
                placeholder = stringResource(R.string.placeholderEmail),
                isEmail = true,
                modifier = Modifier.Companion.fillMaxWidth()
            )

            if (!isEmailValid && email.isNotEmpty()) {
                ErrorMessage(text = stringResource(R.string.correctEmail))
            }


            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 6
                },
                label = stringResource(R.string.labelPass),
                placeholder = stringResource(R.string.placeholderPass),
                isPassword = true,
                modifier = Modifier.Companion.fillMaxWidth()
            )

            if (!isPasswordValid && password.isNotEmpty()) {
                ErrorMessage(text = stringResource(R.string.correctPassword))
            }


            Spacer(modifier = Modifier.Companion.height(dimensions.spacingMedium))


            AppButton(
                text = stringResource(R.string.signInBtn),
                onClick = {
                    if (isValidEmail(email) && password.length >= 6) {
                        onLoginClick(email, password)
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty() && isValidEmail(email) && password.length >= 6
            )
        }
        Spacer(modifier = Modifier.Companion.height(dimensions.spacingLarge))
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.Companion.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.haveAcc),
                    color = colors.textPrimary,
                    style = typography.bodySmall
                )

                Text(
                    text = stringResource(R.string.labelSignUp),
                    color = colors.primary,
                    style = typography.bodySmall,
                    modifier = Modifier.Companion.clickable { }
                )
            }

            Text(
                text = stringResource(R.string.labelForgotPass),
                color = colors.primary,
                style = typography.bodySmall,
                modifier = Modifier.Companion
                    .padding(top = 8.dp)
            )
        }
        Spacer(Modifier.Companion.height(dimensions.spacingExtraExtraLarge))
        Divider(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(1.dp),
            color = Color(0xFF4D555E),
            thickness = 1.dp
        )
        Spacer(Modifier.Companion.height(dimensions.spacingExtraExtraLarge))

        OtherAuthForm(snackbarHostState = snackbarHostState, viewModel = viewModel)
    }
}