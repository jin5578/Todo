package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksByStateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(isCompleted: Boolean): Flow<List<Task>> =
        taskRepository.getTasksByState(isCompleted)
}
