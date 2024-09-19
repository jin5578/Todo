package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.model.SortTask
import javax.inject.Inject

class UpdateSortTaskUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(sortTask: SortTask) =
        systemRepository.updateSortTask(sortTask)
}