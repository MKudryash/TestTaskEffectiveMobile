package com.example.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppShapes
import com.example.design.theme.LocalAppTypography

@Composable
fun ContentCard(
    title: String,
    description: String,
    price: Int,
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current

    Text(
        text = title,
        color = colors.textPrimary,
        style = typography.labelLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

    Text(
        text = description,
        color = colors.textSecondary,
        style = typography.bodySmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$price ₽",
            color = colors.textPrimary,
            style = typography.labelLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Подробнее ",
                color = colors.primary,
                style = typography.buttonSmall
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(10.dp)
            )
        }
    }
}