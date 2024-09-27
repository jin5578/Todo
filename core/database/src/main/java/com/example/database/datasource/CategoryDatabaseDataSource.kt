package com.example.database.datasource

import com.example.database.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryDatabaseDataSource {
    fun getAllCategory(): Flow<List<CategoryEntity>>
    suspend fun insertCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
}