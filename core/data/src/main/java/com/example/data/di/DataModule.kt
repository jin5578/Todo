package com.example.data.di

import com.example.datastore.datasource.DefaultSettingPreferencesDataSource
import com.example.datastore.datasource.SettingPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun bindsSettingsPreferencesDataSource(
        dataSource: DefaultSettingPreferencesDataSource,
    ): SettingPreferencesDataSource
}