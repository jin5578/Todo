package com.example.widget.utils

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import com.example.widget.TodoWidgetReceiver

fun sendWidgetUpdateCommand(context: Context) {
    context.sendBroadcast(
        Intent(
            context,
            TodoWidgetReceiver::class.java
        ).setAction(
            AppWidgetManager.ACTION_APPWIDGET_UPDATE
        )
    )
}