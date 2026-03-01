package com.example.design.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppShapes
import java.nio.file.Files.size

enum class SocialButtonType {
    VK, OK
}

@Composable
fun SocialButton(
    type: SocialButtonType,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val dimensions = LocalAppDimensions.current
    val shape = LocalAppShapes.current

    val backgroundColor = when (type) {
        SocialButtonType.VK -> Color(0xFF2683ED)
        SocialButtonType.OK -> Color.Transparent
    }

    val backgroundBrush = if (type == SocialButtonType.OK) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF98509),
                Color(0xFFF95D00)
            )
        )
    } else null


    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.buttonHeight)
            .clip(shape.buttonShape)
            .then(
                if (backgroundBrush != null) {
                    Modifier.background(brush = backgroundBrush, shape = shape.buttonShape)
                } else Modifier
            )
,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),

        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = icon,
                contentDescription = when (type) {
                    SocialButtonType.VK -> "ВКонтакте"
                    SocialButtonType.OK -> "Одноклассники"
                },
                tint = Color.White
            )
        }
    }
}