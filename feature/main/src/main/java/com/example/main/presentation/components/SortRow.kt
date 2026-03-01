package com.example.main.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.theme.AppColors
import com.example.design.theme.AppDimensions
import com.example.design.theme.AppTypography

@Composable
 fun SortRow(
    sortAscending: Boolean,
    onSortClick: () -> Unit,
    colors: AppColors,
    dimensions: AppDimensions,
    typography: AppTypography
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(vertical = dimensions.spacingLarge)
            .clickable { onSortClick() },
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "По дате добавления",
            color = colors.primary,
            style = typography.bodyMedium,
        )
        Spacer(modifier = Modifier.Companion.width(dimensions.spacingSmall))
        Icon(
            painter = painterResource(id = R.drawable.arrow_down_up),
            contentDescription = "Sort",
            tint = colors.primary,
            modifier = Modifier.Companion.size(16.dp)
        )
    }
}