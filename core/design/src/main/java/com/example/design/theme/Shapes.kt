package com.example.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

data class AppShapes(
    val textFieldShape: RoundedCornerShape =
        androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
    val buttonShape: RoundedCornerShape =
        androidx.compose.foundation.shape.RoundedCornerShape(30.dp),

    val cardShape: RoundedCornerShape =
        androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
)
val LocalAppShapes = compositionLocalOf { AppShapes() }
