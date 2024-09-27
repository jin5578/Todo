package com.example.data_api.repository

import com.example.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategory(): Flow<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}