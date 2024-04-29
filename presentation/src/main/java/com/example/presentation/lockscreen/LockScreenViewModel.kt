package com.example.presentation.lockscreen

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
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

    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) }
    }

    fun onUnlockClick() = intent {
        if (state.password == "111") {
            postSideEffect(LockScreenSideEffect.IntentToMain)
        } else {
            postSideEffect(LockScreenSideEffect.Toast(message = "비밀번호가 다릅니다. 다시 확인해주세요."))
        }
    }
}

@Immutable
data class LockScreenState(
    val password: String = ""
)

sealed interface LockScreenSideEffect {
    class Toast(val message: String) : LockScreenSideEffect
    object IntentToMain : LockScreenSideEffect
}