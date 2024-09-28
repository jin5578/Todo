package com.example.domain.usecase

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.tasks.Tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<Tasks> {
        return combine(
            taskRepository.getAllTask(),
            categoryRepository.getAllCategory()
        ) { tasks, categories ->
            Tasks(tasks = tasks, categories = categories)
        }
    }
}