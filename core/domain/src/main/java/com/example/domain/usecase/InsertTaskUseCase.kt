package com.example.domain.usecase

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) =
        taskRepository.insertTask(task)
}