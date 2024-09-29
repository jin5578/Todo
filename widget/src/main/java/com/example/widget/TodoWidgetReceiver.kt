package com.example.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.widget.di.WidgetModule
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class TodoWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TodoWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        getIncompletedTaskAndUpdateWidget(context, glanceAppWidget)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        getIncompletedTaskAndUpdateWidget(context, glanceAppWidget)
        super.onReceive(context, intent)
    }

    companion object {
        const val KEY_TASK_IDS = "TASK_IDS"
    }
}

private fun getIncompletedTaskAndUpdateWidget(
    context: Context,
    glanceAppWidget: GlanceAppWidget
) {
    val widgetModule: WidgetModule = EntryPoints.get(
        context.applicationContext,
        WidgetModule::class.java
    )

    CoroutineScope(Dispatchers.IO).launch {
        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(TodoWidget::class.java)
        widgetModule.getTasksByStateUseCase().invoke(false).collect { tasks ->
            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) {
                    val set: Set<String> = mutableSetOf<String>().apply {
                        tasks.tasks.forEach { task ->
                            this.add(task.id.toString())
                        }
                    }
                    it.toMutablePreferences().apply {
                        this[stringSetPreferencesKey(TodoWidgetReceiver.KEY_TASK_IDS)] =
                            set
                    }
                }
            }
            glanceAppWidget.updateAll(context)
        }
    }
}