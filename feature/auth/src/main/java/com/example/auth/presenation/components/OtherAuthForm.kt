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

@Composable
fun OtherAuthForm(
    snackbarHostState: SnackbarHostState? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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

// Preview для светлой темы
@Preview(
    name = "OtherAuthForm Light",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun OtherAuthFormPreview() {
    OtherAuthForm(
        snackbarHostState = SnackbarHostState(),
        modifier = Modifier.padding(16.dp)
    )
}

// Preview для темной темы
@Preview(
    name = "OtherAuthForm Dark",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun OtherAuthFormDarkPreview() {
    OtherAuthForm(
        snackbarHostState = SnackbarHostState(),
        modifier = Modifier.padding(16.dp)
    )
}

// Preview без Snackbar (с Toast)
@Preview(
    name = "OtherAuthForm No Snackbar",
    showBackground = true
)
@Composable
fun OtherAuthFormNoSnackbarPreview() {
    OtherAuthForm(
        snackbarHostState = null,
        modifier = Modifier.padding(16.dp)
    )
}

// Preview с кастомными размерами
@Preview(
    name = "OtherAuthForm Custom Size",
    showBackground = true,
    widthDp = 400,
    heightDp = 100
)
@Composable
fun OtherAuthFormCustomSizePreview() {
    OtherAuthForm(
        snackbarHostState = SnackbarHostState(),
        modifier = Modifier.padding(16.dp)
    )
}