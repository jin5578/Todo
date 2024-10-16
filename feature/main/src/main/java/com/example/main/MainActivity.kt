package com.example.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.TodoTheme
import com.example.model.Theme
import com.example.model.ThemeType
import com.example.widget.TodoWidget.Companion.KEY_TASK_ID
import com.example.widget.utils.sendWidgetUpdateCommand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var notificationManager: NotificationManager

    private val viewModel by viewModels<MainViewModel>()
    private val taskIdFromWidget: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        createNotificationChannel()
        sendWidgetUpdateCommand(application)

        intent.getStringExtra(KEY_TASK_ID)?.let {
            taskIdFromWidget.value = it
            intent.removeExtra(KEY_TASK_ID)
        }

        setContent {
            val theme by viewModel.theme.collectAsStateWithLifecycle(
                initialValue = Theme(ThemeType.SYSTEM),
                lifecycleOwner = this
            )

            val navigator = rememberMainNavigator()
            val taskId = taskIdFromWidget.collectAsStateWithLifecycle().value

            LaunchedEffect(taskId) {
                taskId?.let {
                    navigator.navigateEditTask(it.toLong())
                }
            }

            TodoTheme(theme) {
                MainScreen(navigator)
            }
        }
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "todo-notification"
        private const val CHANNEL_NAME = "TODO Reminder"
    }
}