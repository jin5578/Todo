package com.example.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.example.design_system.R as DesignSystemR

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {
    fun createNotificationChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(id: String, title: String, time: String) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(DesignSystemR.drawable.svg_clock)
            .setStyle(NotificationCompat.BigTextStyle().bigText(time))
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        notificationManager.notify(
            id.hashCode(),
            notificationBuilder.build()
        )
    }

    companion object {
        private const val CHANNEL_ID = "todo-notification"
        private const val CHANNEL_NAME = "TODO Reminder"
    }
}