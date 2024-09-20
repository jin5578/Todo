package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.addtask.AddTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetAddTaskDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(date: LocalDate): Flow<AddTask> = combine(
        systemRepository.getAddTaskSystem(),
        taskRepository.getTaskCountByDate(date)
    ) { addTaskSystem, taskCount ->
        AddTask(
            date,
            taskCount,
            addTaskSystem
        )
    }
}