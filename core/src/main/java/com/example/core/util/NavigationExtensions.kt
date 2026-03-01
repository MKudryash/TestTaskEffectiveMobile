package com.example.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrlSafely(url: String): Result<Unit> {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Не найдено приложение для открытия ссылки"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}