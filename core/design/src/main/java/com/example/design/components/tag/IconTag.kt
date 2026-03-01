package com.example.effectivemobiletesttask.core.design.components.tag

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design.components.tag.GlassTag

@Composable
fun IconTag(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    activeColor: Color = Color(0xFF12B956),
    inactiveColor: Color = Color.White,
    backgroundColor: Color = Color(0xFF32333A),
    iconSize: Dp = 16.dp,
    containerSize: Dp = 32.dp,
    cornerRadius: Dp = 12.dp,
    blurRadius: Dp = 16.dp,
    alpha: Float = 0.3f
) {
    val iconTint = if (isActive) activeColor else inactiveColor

    GlassTag(
        modifier = modifier.size(containerSize),
        backgroundColor = backgroundColor,
        blurRadius = blurRadius,
        cornerRadius = cornerRadius,
        alpha = alpha,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }
        })
}