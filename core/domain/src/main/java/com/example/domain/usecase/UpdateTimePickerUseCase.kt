package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.model.TimePicker
import javax.inject.Inject

class UpdateTimePickerUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(timePicker: TimePicker) =
        systemRepository.updateTimePicker(timePicker)
}