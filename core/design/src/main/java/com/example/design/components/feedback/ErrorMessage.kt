package com.example.design.components.feedback


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppTypography

@Composable
fun ErrorMessage(
    text: String,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current

    Text(
        text = text,
        color = colors.error,
        style = typography.caption,
        modifier = modifier.padding(start = 16.dp)
    )
}