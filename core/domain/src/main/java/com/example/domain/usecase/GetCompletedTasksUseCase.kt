package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCompletedTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): Flow<List<Task>> =
        flow { emit(taskRepository.getCompletedTasks()) }
}
