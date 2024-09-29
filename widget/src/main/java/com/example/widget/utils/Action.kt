package com.example.widget.utils

import android.content.Context
import android.content.Intent
import androidx.glance.action.Action
import androidx.glance.appwidget.action.actionStartActivity
import com.example.widget.TodoWidget

internal fun actionLaunchIntentForPackage(context: Context): Action = actionStartActivity(
    Intent(
        context.packageManager.getLaunchIntentForPackage(
            context.packageName
        )
    )
)

internal fun actionStartActivityWithTaskId(
    context: Context,
    taskId: Long
): Action =
    actionStartActivity(
        Intent(
            context.packageManager.getLaunchIntentForPackage(
                context.packageName
            )
        ).putExtra(TodoWidget.KEY_TASK_ID, taskId.toString())
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )