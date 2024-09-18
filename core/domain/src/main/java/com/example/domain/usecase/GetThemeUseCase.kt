package com.example.domain.usecase

import com.example.data_api.repository.SettingRepository
import com.example.model.Theme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    operator fun invoke(): Flow<Theme> =
        settingRepository.getTheme()
}