package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import javax.inject.Inject

class UpdateNotificationStateUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) =
        systemRepository.updateNotificationState(isEnabled)
}