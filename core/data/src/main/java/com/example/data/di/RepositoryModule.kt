package com.example.data.di

import com.example.data.repository.DefaultCategoryRepository
import com.example.data.repository.DefaultSystemRepository
import com.example.data.repository.DefaultTaskRepository
import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.CategoryDatabaseDataSource
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.datastore.datasource.SystemPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun providesSystemRepository(
        systemDataSource: SystemPreferencesDataSource,
    ): SystemRepository =
        DefaultSystemRepository(systemDataSource)

    @Provides
    @Singleton
    fun providesTaskRepository(
        taskDataSource: TaskDatabaseDataSource,
    ): TaskRepository =
        DefaultTaskRepository(taskDataSource)

    @Provides
    @Singleton
    fun providesCategoryRepository(
        categoryDataSource: CategoryDatabaseDataSource,
    ): CategoryRepository =
        DefaultCategoryRepository(categoryDataSource)
}