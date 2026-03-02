package com.example.design.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF12B956)
val BackgroundDark = Color(0xFF1E1E1E)
val Dark = Color(0xFF151515)
val SurfaceDark = Color(0xFF24252A)
val TextPrimaryWhite = Color.White
val TextSecondaryGray = Color(0xFF8E8E93)
val TextHintGray = Color(0xFF666666)
val TextFieldBackground = Color(0xFF32333A)
val ErrorRed = Color(0xFFFF3B30)
val VkBlue = Color(0xFF2683ED)
val OkGradientStart = Color(0xFFF98509)
val OkGradientEnd = Color(0xFFF95D00)

data class AppColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textHint: Color,
    val textFieldBackground: Color,
    val error: Color,
    val socialVk: Color,
    val socialOkStart: Color,
    val socialOkEnd: Color,
    val favoriteActive: Color,
    val divider: Color,
    val dark:Color
)

val DarkColorPalette = AppColors(
    primary = PrimaryGreen,
    background = BackgroundDark,
    surface = SurfaceDark,
    textPrimary = TextPrimaryWhite,
    textSecondary = TextSecondaryGray,
    textHint = TextHintGray,
    textFieldBackground = TextFieldBackground,
    error = ErrorRed,
    socialVk = VkBlue,
    socialOkStart = OkGradientStart,
    socialOkEnd = OkGradientEnd,
    favoriteActive = PrimaryGreen,
    divider = Color(0xFF4D555E),
    dark = Dark
)
val LocalAppColors = compositionLocalOf { DarkColorPalette }