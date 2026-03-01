package com.example.design.components.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design.theme.LocalAppColors
import com.example.navigation.NavItem

@Composable
fun NavigationItem(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) colors.surface else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.textSecondary,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { onClick() }
            .padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(backgroundColor)
        ) {
            Icon(
                painter = icon,
                contentDescription = item.title,
                tint = contentColor,
                modifier = Modifier.Companion.size(64.dp, 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.title,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
            color = contentColor
        )
    }
}