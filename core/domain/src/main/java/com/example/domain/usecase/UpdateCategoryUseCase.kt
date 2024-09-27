package com.example.domain.usecase

import com.example.data_api.repository.CategoryRepository
import com.example.model.Category
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) =
        categoryRepository.updateCategory(category)
}