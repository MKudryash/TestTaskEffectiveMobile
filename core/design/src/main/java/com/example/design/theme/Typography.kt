package com.example.design.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.design.R

val robotoFamily = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.SemiBold),
)
data class AppTypography(
    val labelLarge: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp
    ),
    val displayMedium: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (0).sp
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.25).sp
    ),
    val bodySmall: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = robotoFamily,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    val button: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ) ,
    val buttonSmall: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 15.sp,
        letterSpacing = 0.4.sp
    )
)
val LocalAppTypography = compositionLocalOf { AppTypography() }
