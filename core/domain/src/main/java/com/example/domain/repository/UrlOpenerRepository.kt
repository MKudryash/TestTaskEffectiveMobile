package com.example.domain.repository

interface UrlOpenerRepository {
    suspend fun openUrl(url: String): Result<Unit>
}