package com.example.coursedetail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design.R
import com.example.design.components.tag.GlassTag
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.effectivemobiletesttask.core.design.components.tag.IconTag



@Composable
fun HeaderInformCourse(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit,
    onBackNavigate: () -> Unit,
    rating: Double? = 4.9,
    date: String,
) {

    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current
    val typography = LocalAppTypography.current
    var favoriteState by remember { mutableStateOf(isFavorite) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cover_big),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(start = dimensions.spacingMedium,top = 56.dp, end =  dimensions.spacingMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconTag(
                    icon = painterResource(
                        id = R.drawable.arrow_left
                    ),
                    onClick = {
                        onBackNavigate()
                    },
                    isActive = true,
                    inactiveColor = colors.dark,
                    activeColor = colors.dark,
                    cornerRadius = dimensions.cornerRadiusIcon,
                    backgroundColor = Color.White,
                    alpha = 1f,
                    blurRadius = 0.01.dp,
                    iconSize = 24.dp,
                    modifier = Modifier.size(44.dp)
                )

                IconTag(
                    icon = painterResource(
                        id = if (favoriteState) R.drawable.bookmark_fill
                        else R.drawable.bookmark
                    ),
                    onClick = {
                        favoriteState = !favoriteState
                        onFavoriteClick()
                    },
                    isActive = favoriteState,
                    inactiveColor = colors.dark,
                    activeColor = colors.dark,
                    cornerRadius = dimensions.cornerRadiusIcon,
                    backgroundColor = Color.White,
                    alpha = 1f,
                    iconSize = 24.dp,
                    blurRadius = 0.01.dp,
                    modifier = Modifier.size(44.dp)
                )

            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensions.spacingMedium),
            horizontalArrangement = Arrangement.spacedBy(dimensions.spacingMedium)
        ) {
            GlassTag(content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensions.spacingSmall)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = rating.toString(),
                        color = colors.textPrimary,
                        style = typography.bodySmall
                    )
                }
            }
            )

            GlassTag(content = {
                Text(
                    text = date,
                    style = typography.bodySmall,
                    color = colors.textPrimary
                )
            })
        }
    }


}

@Preview
@Composable
fun HeaderInformCoursePreview(){
    HeaderInformCourse(Modifier, false,{},{}, 4.4, "22 мая 2025")
}