package com.example.auth.presenation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auth.R
import com.example.design.components.button.SocialButton
import com.example.design.components.button.SocialButtonType
import kotlinx.coroutines.launch
import com.example.core.util.openUrlSafely
import com.example.design.theme.LocalAppDimensions

@Composable
fun OtherAuthForm(
    snackbarHostState: SnackbarHostState? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dimensions = LocalAppDimensions.current

    val handleUrlOpen = { url: String ->
        val result = context.openUrlSafely(url)
        result.onFailure { exception ->
            val errorMessage = exception.message ?: "Ошибка при открытии ссылки"

            if (snackbarHostState != null) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialButton(
            type = SocialButtonType.VK,
            icon = painterResource(R.drawable.vk),
            onClick = { handleUrlOpen("https://vk.com/") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.1f))

        SocialButton(
            type = SocialButtonType.OK,
            icon = painterResource(R.drawable.ok),
            onClick = { handleUrlOpen("https://ok.ru/") },
            modifier = Modifier.weight(1f)
        )
    }
}
