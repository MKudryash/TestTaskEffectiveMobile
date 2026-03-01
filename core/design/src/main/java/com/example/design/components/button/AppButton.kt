package com.example.design.components.button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    containerColor: Color = LocalAppColors.current.primary,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(LocalAppDimensions.current.cornerRadius)
) {
    val dimensions = LocalAppDimensions.current
    val typography = LocalAppTypography.current

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.buttonHeight)
            .clip(shape),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.3f),
            contentColor = contentColor
        ),
        enabled = enabled,
        shape = shape
    ) {
        Text(
            text = text,
            style = typography.button
        )
    }
}