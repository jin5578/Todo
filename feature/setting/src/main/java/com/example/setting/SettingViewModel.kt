package com.example.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSettingDataUseCase
import com.example.domain.usecase.UpdateThemeUseCase
import com.example.model.ThemeType
import com.example.setting.model.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getSettingDataUseCase: GetSettingDataUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<SettingUiState> =
        MutableStateFlow(SettingUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchSetting()
    }

    private fun fetchSetting() {
        viewModelScope.launch {
            getSettingDataUseCase().map { settingSystem ->
                SettingUiState.Success(
                    theme = settingSystem.theme,
                    sleepTime = settingSystem.sleepTime,
                    timePicker = settingSystem.timePicker,
                    buildVersion = settingSystem.buildVersion
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateTheme(themeType: ThemeType) {
        viewModelScope.launch {
            updateThemeUseCase(themeType)
        }
    }
}