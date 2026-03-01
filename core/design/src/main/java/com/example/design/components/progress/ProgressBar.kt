package com.example.design.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.ranges.coerceIn

@Composable
fun ProgressBar(
    completionPercentage: Int,
    modifier: Modifier = Modifier.Companion,
    height: Dp = 4.dp,
    completedColor: Color = Color(0xFF4CAF50),
    remainingColor: Color = Color(0xFFE0E0E0).copy(alpha = 0.3f),
    spacing: Dp = 2.dp,

    ) {
    val validPercentage = completionPercentage.coerceIn(0, 100)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Box(
            modifier = Modifier.Companion
                .fillMaxHeight()
                .weight(validPercentage / 100f)
                .background(
                    color = completedColor,
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Box(
            modifier = Modifier.Companion
                .fillMaxHeight()
                .weight((100 - validPercentage) / 100f)
                .background(
                    color = remainingColor,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
                )
        )
    }
}