package com.example.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Светлая цветовая палитра
val LightColorPalette = AppColors(
    primary = PrimaryGreen,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    textPrimary = Color.Black,
    textSecondary = Color(0xFF666666),
    textHint = Color(0xFF999999),
    textFieldBackground = Color(0xFFEEEEEE),
    error = ErrorRed,
    socialVk = VkBlue,
    socialOkStart = OkGradientStart,
    socialOkEnd = OkGradientEnd,
    favoriteActive = PrimaryGreen,
    divider = Color(0xFFE0E0E0)
)

// Главная тема
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: AppTypography = AppTypography(),
    dimensions: AppDimensions = AppDimensions(),
    shapes: AppShapes = AppShapes(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    // Material Theme 3 цветовая схема
    val materialColorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colors.primary,
            background = colors.background,
            surface = colors.surface,
            error = colors.error,
            onPrimary = colors.textPrimary,
            onBackground = colors.textPrimary,
            onSurface = colors.textPrimary
        )
    } else {
        lightColorScheme(
            primary = colors.primary,
            background = colors.background,
            surface = colors.surface,
            error = colors.error,
            onPrimary = colors.textPrimary,
            onBackground = colors.textPrimary,
            onSurface = colors.textPrimary
        )
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppDimensions provides dimensions,
        LocalAppShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = MaterialTheme.typography,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}