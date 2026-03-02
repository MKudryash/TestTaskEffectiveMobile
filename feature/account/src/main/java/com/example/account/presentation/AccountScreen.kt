package com.example.account.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.account.presentation.components.AccountAction
import com.example.design.theme.LocalAppColors
import com.example.design.theme.LocalAppDimensions
import com.example.design.theme.LocalAppTypography

@Composable
fun  AccountScreen () {
    val colors = LocalAppColors.current
    val typography = LocalAppTypography.current
    val dimensions = LocalAppDimensions.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = dimensions.spacingLarge, end = dimensions.spacingLarge, top = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
            Text(
                text = "Профиль",
                color = colors.textPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                style = typography.displayMedium,
                textAlign = TextAlign.Start
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth().padding(top = dimensions.spacingLarge, bottom = dimensions.spacingExtraLarge),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AccountAction(
                        text = "Написать в поддержку",
                        onClick = {}
                    )


                    HorizontalDivider(
                        Modifier.padding(  start = dimensions.spacingLarge,
                            end =  dimensions.spacingLarge-4.dp,),
                        thickness = 1.dp,
                        color = colors.divider
                    )


                    AccountAction(
                        text = "Настройки",
                        onClick = {}
                    )


                    HorizontalDivider(
                        Modifier.padding(  start = dimensions.spacingLarge,
                            end =  dimensions.spacingLarge-4.dp,),
                        thickness = 1.dp,
                        color = colors.divider
                    )

                    AccountAction(
                        text = "Выйти из аккаунта",
                        onClick = {}
                    )
                }
            }
            Text(
                text = "Ваши курсы",
                color = colors.textPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                style = typography.displayMedium,
                textAlign = TextAlign.Start
            )
        }

}