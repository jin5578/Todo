package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.edittask.EditTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEditTaskDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(taskId: Long): Flow<EditTask> {
        return combine(
            systemRepository.getEditTaskSystem(),
            flow {
                emit(taskRepository.getTaskById(taskId))
            }
        ) { editTaskSystem, task ->
            EditTask(
                task = task,
                editTaskSystem = editTaskSystem
            )
        }
    }
}