package com.example.design.components.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.theme.LocalAppColors
import com.example.navigation.NavItem

@Composable
fun BottomNavigationBar(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true // Добавляем параметр видимости
) {
    val colors = LocalAppColors.current

    // Если невидима - не отображаем
    if (!visible) return

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = colors.divider,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.5.dp.toPx()
                )
            },
        color = colors.surface,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItemWithIcon(
                item = NavItem.Main,
                selected = selectedItem == NavItem.Main,
                onClick = { onItemSelected(NavItem.Main) },
                iconRes = R.drawable.main
            )

            NavItemWithIcon(
                item = NavItem.Favorite,
                selected = selectedItem == NavItem.Favorite,
                onClick = { onItemSelected(NavItem.Favorite) },
                iconRes = R.drawable.favorite
            )

            NavItemWithIcon(
                item = NavItem.Account,
                selected = selectedItem == NavItem.Account,
                onClick = { onItemSelected(NavItem.Account) },
                iconRes = R.drawable.account
            )
        }
    }
}

@Composable
private fun NavItemWithIcon(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    iconRes: Int,
) {
    NavigationItem(
        item = item,
        selected = selected,
        onClick = onClick,
        icon = painterResource(id = iconRes)
    )
}