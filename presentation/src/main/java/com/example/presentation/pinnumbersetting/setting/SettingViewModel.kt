package com.example.presentation.pinnumbersetting.setting

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.pinnumbersetting.ClearPinNumberUseCase
import com.example.domain.usecase.pinnumbersetting.SetPinNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val setPinNumberUseCase: SetPinNumberUseCase,
    private val clearPinNumberUseCase: ClearPinNumberUseCase
) : ViewModel(), ContainerHost<SettingScreenState, SettingSideEffect> {
    override val container: Container<SettingScreenState, SettingSideEffect> = container(
        initialState = SettingScreenState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(SettingSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onPinNumberChange(pinNumber: String) = blockingIntent {
        reduce { state.copy(pinNumber = pinNumber) }
    }

    fun onChangeClick() = intent {
        setPinNumberUseCase(state.pinNumber)
        postSideEffect(SettingSideEffect.IntentToMain)
    }

    fun onClearClick() = intent {
        clearPinNumberUseCase()
        postSideEffect(SettingSideEffect.IntentToMain)
    }
}

@Immutable
data class SettingScreenState(
    val pinNumber: String = ""
)

sealed interface SettingSideEffect {
    class Toast(val message: String) : SettingSideEffect
    object IntentToMain : SettingSideEffect
}
