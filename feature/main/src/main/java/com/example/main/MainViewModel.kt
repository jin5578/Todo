package com.example.main

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetThemeUseCase
import com.example.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase
) : ViewModel() {
    val theme: Flow<Theme> = getThemeUseCase()
}