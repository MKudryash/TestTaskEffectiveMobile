package com.example.design.components.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.graphics.BlurMaskFilter

@Composable
fun GlassTag(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF32333A),
    blurRadius: Dp = 8.dp,
    cornerRadius: Dp = 12.dp,
    alpha: Float = 0.3f,
    horizontalPadding: Dp = 8.dp,
    verticalPadding: Dp = 4.dp
) {
    val shape = RoundedCornerShape(cornerRadius)
    val blurPx = with(LocalDensity.current) { blurRadius.toPx() }

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = alpha + 0.3f),
                        backgroundColor.copy(alpha = alpha + 0.3f)
                    )
                ),
                shape = shape
            )
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
                        color = backgroundColor.copy(alpha = alpha).toArgb()
                        style = android.graphics.Paint.Style.FILL
                    }
                    canvas.nativeCanvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        cornerRadius.toPx(), cornerRadius.toPx(),
                        paint
                    )
                }
            }
            .clip(shape)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}