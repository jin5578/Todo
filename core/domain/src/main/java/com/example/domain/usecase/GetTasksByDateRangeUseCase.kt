package com.example.domain.usecase

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.tasks.Tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetTasksByDateRangeUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(fromDate: LocalDate, toDate: LocalDate): Flow<Tasks> {
        return combine(
            taskRepository.getTasksByDateRange(fromDate = fromDate, toDate = toDate),
            categoryRepository.getAllCategory()
        ) { tasks, categories ->
            Tasks(tasks = tasks, categories = categories)
        }
    }
}