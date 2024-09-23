package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksByDateRangeUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(fromDate: LocalDate, toDate: LocalDate): Flow<List<Task>> =
        taskRepository.getTasksByDateRange(fromDate = fromDate, toDate = toDate)
}