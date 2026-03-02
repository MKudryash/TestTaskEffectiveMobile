package com.example.coursedetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coursedetail.domain.model.CourseDetail
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography
import com.example.design.R
import com.example.design.components.button.AppButton
import com.example.effectivemobiletesttask.core.design.components.tag.IconTag

@Composable
fun CourseDetailContent(course: CourseDetail, onNavigateBack: () -> Unit) {
    val colors = LocalAppColors.current
    val dimensions = LocalAppDimensions.current
    val typography = LocalAppTypography.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderInformCourse(
                    modifier = Modifier.fillMaxHeight(0.4f),
                    isFavorite = course.isFavorite,
                    rating = course.rating,
                    onFavoriteClick = {},
                    date = course.getFormattedPublishDate(),
                    onBackNavigate = onNavigateBack)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensions.spacingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = course.title,
                        color = colors.textPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensions.spacingLarge),
                        style = typography.displayMedium,
                        textAlign = TextAlign.Start
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (course.imageAuthorUrl == null) {
                            IconTag(
                                icon = painterResource(
                                    id = R.drawable.bookmark_fill
                                ),
                                onClick = {

                                },
                                isActive = false,
                                inactiveColor = colors.dark,
                                activeColor = colors.dark,
                                cornerRadius = dimensions.cornerRadiusIcon,
                                backgroundColor = Color(0xFF9373F8),
                                alpha = 1f,
                                blurRadius = 0.01.dp
                            )
                        }
                        Column {
                            Text(
                                "Автор",
                                style = typography.bodySmall.copy(
                                    color = Color(0xFFF2F2F3),
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                course.author!!,
                                style = typography.bodyMedium.copy(
                                    color = colors.textPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(dimensions.spacingExtraExtraLarge))


                    AppButton(
                        text = "Начать курс",
                        onClick = {},
                        )
                    Spacer(Modifier.height(dimensions.spacingMedium))
                    AppButton(
                        text = "Перейти на платформу",
                        onClick = {},
                        containerColor = colors.textFieldBackground
                        )

                    Text(
                        text = "О курсе",
                        color = colors.textPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensions.spacingLarge),
                        style = typography.displayMedium,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = course.description,
                        style = typography.bodySmall.copy(lineHeight = 20.sp, letterSpacing = 0.25.sp, color = Color(0xFFF2F2F3)),

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CourseDetailContentPreview() {
    CourseDetailContent(
        CourseDetail(
            id = 100,
            title = "Java-разработчик с нуля",
            description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
            price = 999,
            priceString = "999",
            isFavorite = false,
            rating = 4.9,
            startDate = "2024-05-22",
            publishDate = "2024-02-02",
        ), {}
    )
}