package com.example.network.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.domain.repository.UrlOpenerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UrlOpenerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UrlOpenerRepository {

    override suspend fun openUrl(url: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

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
}