package com.example.presentation.lockscreen

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
class LockScreenViewModel @Inject constructor(
    private val getPinNumberUseCase: GetPinNumberUseCase
) : ViewModel(), ContainerHost<LockScreenState, LockScreenSideEffect> {
    override val container: Container<LockScreenState, LockScreenSideEffect> = container(
        initialState = LockScreenState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(LockScreenSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onPinNumberChange(pinNumber: String) = blockingIntent {
        reduce { state.copy(pinNumber = pinNumber) }
    }

    fun onUnlockClick() = intent {
        if (state.pinNumber == getPinNumberUseCase()) {
            postSideEffect(LockScreenSideEffect.IntentToMain)
        } else {
            postSideEffect(LockScreenSideEffect.Toast(message = "PIN Number가 다릅니다. 다시 확인해주세요."))
        }
    }
}

@Immutable
data class LockScreenState(
    val pinNumber: String = ""
)

sealed interface LockScreenSideEffect {
    class Toast(val message: String) : LockScreenSideEffect
    object IntentToMain : LockScreenSideEffect
}