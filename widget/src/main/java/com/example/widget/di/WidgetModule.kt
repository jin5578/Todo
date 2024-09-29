package com.example.widget.di

import com.example.domain.usecase.GetTaskByIdUseCase
import com.example.domain.usecase.GetTasksByStateUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetModule {
    fun getTaskByIdUseCase(): GetTaskByIdUseCase
    fun getTasksByStateUseCase(): GetTasksByStateUseCase
}