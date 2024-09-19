package com.example.data.di

import com.example.database.datasource.DefaultTaskDatabaseDataSource
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.datastore.datasource.DefaultSystemPreferencesDataSource
import com.example.datastore.datasource.SystemPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun bindsSettingsPreferencesDataSource(
        dataSource: DefaultSystemPreferencesDataSource,
    ): SystemPreferencesDataSource

    @Binds
    abstract fun bindsTaskDatabaseDataSource(
        dataSource: DefaultTaskDatabaseDataSource,
    ): TaskDatabaseDataSource
}