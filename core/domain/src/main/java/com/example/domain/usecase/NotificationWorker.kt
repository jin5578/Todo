package com.example.domain.usecase

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.example.design_system.R as DesignSystemR

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val notificationManager: NotificationManager
) : Worker(context, params) {
    override fun doWork(): Result {
        val id = inputData.getString(ID)
        val title = inputData.getString(TITLE)
        val time = inputData.getString(TIME)

        if (id != null && title != null && time != null) {
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(DesignSystemR.drawable.svg_clock)
                .setStyle(NotificationCompat.BigTextStyle().bigText(time))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
            notificationManager.notify(
                id.hashCode(),
                notificationBuilder.build()
            )
            return Result.success()
        }
        return Result.failure()
    }

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TIME = "time"

        private const val CHANNEL_ID = "todo-notification"
    }
}