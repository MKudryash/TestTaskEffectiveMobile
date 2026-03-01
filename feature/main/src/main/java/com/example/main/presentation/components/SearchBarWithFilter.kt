package com.example.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.components.button.IconButton
import com.example.design.components.search.SearchBar
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppShapes

@Composable
fun SearchBarWithFilter(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSearch: (() -> Unit)? = null,
    modifier: Modifier = Modifier.Companion,
    placeholder: String = "Search courses..."
) {
    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current
    val shapes = LocalAppShapes.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {

        Box(
            modifier = Modifier.Companion
                .weight(1f)
                .fillMaxHeight()
                .clip(shapes.buttonShape)
                .background(colors.textFieldBackground)
        ) {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                placeholder = placeholder,
            )
        }

        // Кнопка фильтра
        IconButton(
            onClick = onFilterClick,
            icon = painterResource(R.drawable.filter),
            iconTint = Color.Companion.White,
            rounded = dimensions.cornerRadiusIcon,
            modifier = Modifier.Companion.size(56.dp)
        )
    }
}