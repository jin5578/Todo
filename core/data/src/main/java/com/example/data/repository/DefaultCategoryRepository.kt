package com.example.data.repository

import com.example.data_api.repository.CategoryRepository
import com.example.database.CategoryEntity
import com.example.database.datasource.CategoryDatabaseDataSource
import com.example.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDatabaseDataSource
) : CategoryRepository {
    override fun getAllCategory(): Flow<List<Category>> {
        return categoryDataSource.getAllCategory().map { entities ->
            entities.map { entity ->
                entity.toCategory()
            }
        }
    }

    override suspend fun insertCategory(category: Category) {
        val entity = category.toCategoryEntity()
        categoryDataSource.insertCategory(entity)
    }

    override suspend fun deleteCategory(category: Category) {
        val entity = category.toCategoryEntity()
        categoryDataSource.deleteCategory(entity)
    }

    private fun CategoryEntity.toCategory() = Category(
        id = this.id,
        title = this.title,
        backgroundColor = this.backgroundColor
    )

    private fun Category.toCategoryEntity() = CategoryEntity(
        id = this.id,
        title = this.title,
        backgroundColor = this.backgroundColor
    )
}