package com.example.domain.usecase

import com.example.data_api.repository.SettingRepository
import com.example.model.SortTask
import javax.inject.Inject

class UpdateSortTaskUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(sortTask: SortTask) =
        settingRepository.updateSortTask(sortTask)
}