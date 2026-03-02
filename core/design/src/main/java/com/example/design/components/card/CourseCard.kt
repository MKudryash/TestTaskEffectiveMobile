package com.example.design.components.card


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.design.components.tag.GlassTag
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.effectivemobiletesttask.core.design.components.tag.IconTag
import com.example.design.R

@Composable
fun CourseCard(

    rating: Double? = 4.9,
    date: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    content: @Composable ()-> Unit
) {
    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current
    val typography = LocalAppTypography.current
    var favoriteState by remember { mutableStateOf(isFavorite) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.cardHeight)
            .clip(RoundedCornerShape(dimensions.cornerRadius)),
        shape = RoundedCornerShape(dimensions.cornerRadius),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        onClick = onCardClick
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cover),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = dimensions.cornerRadius, topEnd = dimensions.cornerRadius))
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(dimensions.spacingMedium)
                ) {
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
                        activeColor = colors.favoriteActive,
                        cornerRadius = dimensions.cornerRadiusIcon
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(dimensions.spacingMedium),
                    horizontalArrangement = Arrangement.spacedBy(dimensions.spacingMedium)
                ) {
                    GlassTag(content =  {
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

                    GlassTag (content =  {
                        Text(
                            text = date,
                            style = typography.bodySmall,
                            color = colors.textPrimary
                        )
                    })
                }
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimensions.spacingLarge)
                    .padding(vertical = dimensions.spacingMedium),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                content()

            }
        }
    }
}