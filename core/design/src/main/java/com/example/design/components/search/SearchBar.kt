package com.example.design.components.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.design.R

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (() -> Unit)? = null,
    placeholder: String = "Search...",
) {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = "Поиск",
            tint = colors.textPrimary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(dimensions.spacingMedium+4.dp))

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            textStyle = typography.bodyMedium.copy(
                color = colors.textPrimary
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = colors.textHint,
                            style = typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    innerTextField()
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch?.invoke()
                }
            ),
            singleLine = true
        )
    }
}

