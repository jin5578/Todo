package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val scheduleNotificationWorkUseCase: ScheduleNotificationWorkUseCase,
    private val cancelNotificationWorkUseCase: CancelNotificationWorkUseCase,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
        if (task.isCompleted) {
            cancelNotificationWorkUseCase(task.uuid)
        } else if (task.isRemind) {
            scheduleNotificationWorkUseCase(task)
        }
    }
}