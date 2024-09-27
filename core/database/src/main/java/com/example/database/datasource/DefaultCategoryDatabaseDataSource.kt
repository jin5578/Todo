package com.example.database.datasource

import com.example.database.CategoryDatabase
import com.example.database.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultCategoryDatabaseDataSource @Inject constructor(
    private val categoryDatabase: CategoryDatabase
) : CategoryDatabaseDataSource {
    override fun getAllCategory(): Flow<List<CategoryEntity>> {
        return categoryDatabase.categoryDao().getAllCategory()
    }

    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        categoryDatabase.categoryDao().insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDatabase.categoryDao().deleteCategory(categoryEntity)
    }
}