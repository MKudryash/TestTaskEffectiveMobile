package com.example.auth.domain.manager

import android.content.Context
import javax.inject.Inject

interface ExternalNavigationManager {
    fun openUrl(context: Context, url: String): Result<Unit>
}

class ExternalNavigationManagerImpl @Inject constructor() : ExternalNavigationManager {

    override fun openUrl(context: Context, url: String): Result<Unit> {
        return try {
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Не найдено приложение для открытия ссылки"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}