package com.example.manage_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DeleteCategoryUseCase
import com.example.domain.usecase.GetAllCategoryUseCase
import com.example.domain.usecase.GetCategoryByIdUseCase
import com.example.domain.usecase.InsertCategoryUseCase
import com.example.domain.usecase.UpdateCategoryUseCase
import com.example.manage_categories.model.ManageCategoriesUiState
import com.example.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<ManageCategoriesUiState> =
        MutableStateFlow(ManageCategoriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchManageCategories()
    }

    private fun fetchManageCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase().map { categories ->
                ManageCategoriesUiState.Success(
                    categories = categories.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun insertCategory(title: String, colorValue: ULong) {
        viewModelScope.launch {
            val category = Category(
                title = title,
                colorValue = colorValue,
            )
            insertCategoryUseCase(category)
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            val category = getCategoryByIdUseCase(id)
            deleteCategoryUseCase(category)
        }
    }

    fun updateCategory(id: Long, title: String, colorValue: ULong) {
        viewModelScope.launch {
            val category = Category(id = id, title = title, colorValue = colorValue)
            updateCategoryUseCase(category)
        }
    }
}