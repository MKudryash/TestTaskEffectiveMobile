package com.example.design.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val spacingSmall: Dp = 4.dp,
    val spacingMedium: Dp = 8.dp,
    val spacingLarge: Dp = 16.dp,
    val spacingExtraLarge: Dp = 24.dp,
    val spacingExtraExtraLarge: Dp = 32.dp,

    val buttonHeight: Dp = 48.dp,
    val textFieldHeight: Dp = 56.dp,
    val iconSize: Dp = 24.dp,
    val cornerRadius: Dp = 12.dp,

    val cardHeight: Dp = 236.dp,
    val cardImageHeight: Dp = 118.dp
)

val LocalAppDimensions = compositionLocalOf { AppDimensions() }