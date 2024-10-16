package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val cancelNotificationWorkUseCase: CancelNotificationWorkUseCase,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.deleteTask(task)
        cancelNotificationWorkUseCase(task.uuid)
    }
}