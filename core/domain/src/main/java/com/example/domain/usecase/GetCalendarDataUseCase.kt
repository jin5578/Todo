package com.example.domain.usecase

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.calendar.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCalendarDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<Calendar> {
        return combine(
            systemRepository.getCalendarSystem(),
            taskRepository.getAllTask()
        ) { calendarSystem, tasks ->
            Calendar(
                tasks = tasks,
                calendarSystem = calendarSystem
            )
        }
    }
}