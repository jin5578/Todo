package com.example.manage_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllCategoryUseCase
import com.example.manage_categories.model.ManageCategoriesUiState
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
}