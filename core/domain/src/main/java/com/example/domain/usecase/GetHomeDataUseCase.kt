package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.home.Home
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<Home> = combine(
        taskRepository.getTodayTasks(),
        systemRepository.getHomeSystem(),
    ) { tasks, homeSystem ->
        Home(
            tasks = tasks,
            homeSystem = homeSystem,
        )
    }
}