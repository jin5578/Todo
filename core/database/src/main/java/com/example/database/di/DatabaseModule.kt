package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase =
        Room.databaseBuilder(context, TaskDatabase::class.java, "task")
            .fallbackToDestructiveMigration()
            .build()
}