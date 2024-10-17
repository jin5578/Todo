package com.example.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
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
        val intent = Intent(
            context.packageManager.getLaunchIntentForPackage(
                context.packageName
            )
        ).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                setSmallIcon(DesignSystemR.drawable.svg_check_list)
                color = context.resources.getColor(DesignSystemR.color.black, null)
            } else {
                setSmallIcon(DesignSystemR.drawable.svg_check_list)
            }
            setContentTitle(title)
            setContentText(time)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setGroup(GROUP_KEY_BASIC)
            setContentIntent(pendingIntent)
        }

        val builderSummary = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                setSmallIcon(DesignSystemR.drawable.svg_check_list)
                color = context.resources.getColor(DesignSystemR.color.black, null)
            } else {
                setSmallIcon(DesignSystemR.drawable.svg_check_list)
            }
            setContentTitle(title)
            setContentText(time)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setGroup(GROUP_KEY_BASIC)
            setGroupSummary(true)
            setContentIntent(pendingIntent)
        }

        notificationManager.apply {
            notify(System.currentTimeMillis().toInt(), builder.build())
            notify(BASIC_NOTIFICATION_ID, builderSummary.build())
        }
    }

    fun cancelAll() {
        notificationManager.cancelAll()
    }

    companion object {
        private const val CHANNEL_ID = "todo-notification"
        private const val CHANNEL_NAME = "TODO Reminder"

        private const val GROUP_KEY_BASIC = "com.example.todo.BASIC"
        private const val BASIC_NOTIFICATION_ID = 0
    }
}