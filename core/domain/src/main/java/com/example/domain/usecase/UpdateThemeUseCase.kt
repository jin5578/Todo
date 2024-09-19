package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.model.ThemeType
import javax.inject.Inject

class UpdateThemeUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(themeType: ThemeType) =
        systemRepository.updateTheme(themeType)
}