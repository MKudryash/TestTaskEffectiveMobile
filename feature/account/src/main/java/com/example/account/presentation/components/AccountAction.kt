package com.example.account.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import org.w3c.dom.Text

@Composable
fun AccountAction(
    text: String = "Настройки",
    onClick:()->Unit
) {
    val typography = LocalAppTypography.current
    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current

    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(
            start = dimensions.spacingLarge,
            end =  dimensions.spacingLarge-4.dp,
            top = dimensions.spacingMedium+2.dp,
            bottom = dimensions.spacingMedium+2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = text,
            style = typography.bodyMedium,
            color  = colors.textPrimary
        )
        Icon(
            painter = painterResource(R.drawable.chevron_right),
            contentDescription = null,
            tint = colors.textPrimary,
        )
    }
}