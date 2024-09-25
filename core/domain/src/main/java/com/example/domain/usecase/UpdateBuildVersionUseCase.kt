package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import javax.inject.Inject

class UpdateBuildVersionUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(buildVersion: String) =
        systemRepository.updateBuildVersion(buildVersion)
}