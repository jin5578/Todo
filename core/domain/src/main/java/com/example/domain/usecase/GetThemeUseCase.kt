package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.model.Theme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(): Flow<Theme> =
        systemRepository.getTheme()
}