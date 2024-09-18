package com.example.data.di

import com.example.data.repository.DefaultSettingRepository
import com.example.data_api.repository.SettingRepository
import com.example.datastore.datasource.SettingPreferencesDataSource
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
    fun providesSettingRepository(
        settingDataSource: SettingPreferencesDataSource,
    ): SettingRepository =
        DefaultSettingRepository(settingDataSource)
}