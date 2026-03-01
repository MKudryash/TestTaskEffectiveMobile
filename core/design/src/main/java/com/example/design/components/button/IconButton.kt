package com.example.design.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design.theme.LocalAppColors
import com.example.design.R


@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    icon: Painter = painterResource(R.drawable.filter),
    iconTint: Color? = null,
    iconSize: Dp = 24.dp,
    rounded:Dp  = 30.dp,
    contentAlignment: Alignment = Alignment.Center
) {
    val colors = LocalAppColors.current

    val finalBackgroundColor = backgroundColor ?: colors.textFieldBackground
    val finalIconTint = iconTint ?: colors.textPrimary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(rounded) )
            .background(finalBackgroundColor)
            .clickable { onClick() },
        contentAlignment = contentAlignment
    ) {
        Icon(
            painter = icon,
            contentDescription = "Фильтр",
            tint = finalIconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}
