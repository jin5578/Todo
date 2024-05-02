package com.example.presentation.pinnumbersetting.verification

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.pinnumbersetting.GetPinNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val getPinNumberUseCase: GetPinNumberUseCase
) : ViewModel(), ContainerHost<VerificationScreenState, VerificationSideEffect> {
    override val container: Container<VerificationScreenState, VerificationSideEffect> = container(
        initialState = VerificationScreenState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(VerificationSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onPinNumberChange(pinNumber: String) = blockingIntent {
        reduce { state.copy(pinNumber = pinNumber) }
    }

    fun onVerificationClick() = intent {
        if (state.pinNumber == getPinNumberUseCase()) {
            postSideEffect(VerificationSideEffect.NavigateToSetting)
        } else {
            postSideEffect(VerificationSideEffect.Toast(message = "PIN Number가 다릅니다. 다시 확인해주세요."))
        }
    }
}

@Immutable
data class VerificationScreenState(
    val pinNumber: String = ""
)

sealed interface VerificationSideEffect {
    class Toast(val message: String) : VerificationSideEffect
    object NavigateToSetting : VerificationSideEffect
}