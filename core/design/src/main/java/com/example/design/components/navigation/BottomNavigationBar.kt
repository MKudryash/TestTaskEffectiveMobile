package com.example.design.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.theme.LocalAppColors
import com.example.navigation.NavItem

@Composable
fun BottomNavigationBar(
    selectedItem: NavItem?,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = colors.background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem.items.forEach { item ->
                NavigationItem(
                    item = item,
                    selected = selectedItem == item,
                    onClick = { onItemSelected(item) },
                    icon = when (item) {
                        NavItem.Main -> painterResource(R.drawable.main)
                        NavItem.Favorite -> painterResource(R.drawable.favorite)
                        NavItem.Account -> painterResource(R.drawable.account)
                    }
                )
            }
        }
    }
}