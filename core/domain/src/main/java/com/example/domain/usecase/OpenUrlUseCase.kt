// :core:domain/src/main/java/com/example/domain/usecase/OpenUrlUseCase.kt
package com.example.domain.usecase

import com.example.domain.repository.UrlOpenerRepository
import javax.inject.Inject

class OpenUrlUseCase @Inject constructor(
    private val urlOpenerRepository: UrlOpenerRepository
) {
    suspend operator fun invoke(url: String): Result<Unit> {
        return urlOpenerRepository.openUrl(url)
    }
}