package com.example.design.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.*
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppShapes
import com.example.design.theme.LocalAppTypography

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onImeAction: (() -> Unit)? = null
) {
    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current
    val shapes = LocalAppShapes.current
    val typography = LocalAppTypography.current

    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }


    val visualTransformation = if (isPassword && !passwordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }



    val keyboardOptions = KeyboardOptions(
        keyboardType = when {
            isPassword -> KeyboardType.Password
            isEmail -> KeyboardType.Email
            else -> KeyboardType.Text
        },
        imeAction = if (onImeAction != null) ImeAction.Done else ImeAction.Next
    )

    val keyboardActions = KeyboardActions(
        onDone = { onImeAction?.invoke() }
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensions.spacingMedium)
    ) {
        Text(
            text = label,
            color =  colors.textPrimary,
            style = typography.labelLarge
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
           label = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min= dimensions.textFieldHeight)
                .clip(shapes.textFieldShape)
                .onFocusChanged { isFocused = it.isFocused },
            enabled = enabled,
            visualTransformation = visualTransformation,
            textStyle = typography.bodyMedium.copy(color = colors.textPrimary),
            placeholder = {
                Text(
                    text = placeholder,
                    color = colors.textHint,
                    style = typography.bodyMedium
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.textFieldBackground,
                focusedContainerColor = colors.textFieldBackground,
                unfocusedContainerColor = colors.textFieldBackground,
                cursorColor = colors.primary,
                focusedTrailingIconColor = colors.primary,
                unfocusedTrailingIconColor = colors.textSecondary,
                disabledContainerColor = colors.textFieldBackground.copy(alpha = 0.5f),
                disabledBorderColor = colors.textFieldBackground.copy(alpha = 0.5f),
                disabledPlaceholderColor = colors.textHint.copy(alpha = 0.5f)
            ),
            shape = shapes.textFieldShape
        )
    }
}