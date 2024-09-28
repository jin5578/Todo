package com.example.domain.usecase

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.home.Home
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<Home> {
        return combine(
            systemRepository.getHomeSystem(),
            taskRepository.getTasksByDate(LocalDate.now()),
            categoryRepository.getAllCategory(),
        ) { homeSystem, tasks, categories ->
            Home(
                tasks = tasks,
                categories = categories,
                homeSystem = homeSystem,
            )
        }
    }
}