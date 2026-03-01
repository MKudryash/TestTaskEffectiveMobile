package com.example.auth.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class OpenBrowserUseCase @Inject constructor() {

    operator fun invoke(
        context: Context,
        url: String,
        onError: (String) -> Unit = {}
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                true
            } else {
                onError("Не найдено приложение для открытия ссылки")
                false
            }
        } catch (e: Exception) {
            onError("Ошибка при открытии ссылки: ${e.message}")
            false
        }
    }
}