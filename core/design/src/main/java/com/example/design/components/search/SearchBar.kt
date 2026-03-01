package com.example.design.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Поиск"
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.spacedBy(dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Поле поиска
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(dimensions.cornerRadius))
                .background(colors.textFieldBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensions.spacingLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
               /* Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    tint = colors.textSecondary,
                    modifier = Modifier.size(dimensions.iconSize)
                )*/

                Spacer(modifier = Modifier.width(dimensions.spacingMedium))

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.weight(1f),
                    textStyle = typography.bodyMedium.copy(color = colors.textPrimary),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = colors.textHint,
                                    style = typography.bodyMedium
                                )
                            }
                            innerTextField()
                        }
                    },
                    singleLine = true
                )
            }
        }

        // Кнопка фильтра
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(dimensions.cornerRadius))
                .background(colors.primary)
                .clickable { onFilterClick() },
            contentAlignment = Alignment.Center
        ) {
            /*Icon(
                painter = painterResource(R.drawable.filter),
                contentDescription = "Фильтр",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )*/
        }
    }
}