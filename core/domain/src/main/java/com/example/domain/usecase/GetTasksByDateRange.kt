package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksByDateRange @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(from: LocalDate, to: LocalDate): Flow<List<Task>> =
        flow { emit(taskRepository.getTasksByDateRange(from = from, to = to)) }
}