package com.example.widget

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import com.example.design_system.theme.TodoGlanceTheme
import com.example.widget.TodoWidgetReceiver.Companion.KEY_TASK_IDS
import com.example.widget.component.WidgetTaskCard
import com.example.widget.component.WidgetTitle
import com.example.widget.di.WidgetModule
import com.example.widget.model.WidgetTaskCardUiState
import dagger.hilt.EntryPoints

class TodoWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val widgetModule: WidgetModule = EntryPoints.get(
            context.applicationContext,
            WidgetModule::class.java
        )

        provideContent {
            TodoGlanceTheme {
                val state = currentState(stringSetPreferencesKey(KEY_TASK_IDS))
                var widgetTaskCards: List<WidgetTaskCardUiState> by remember(state) {
                    mutableStateOf(listOf())
                }

                LaunchedEffect(state) {
                    widgetTaskCards = state?.map {
                        WidgetTaskCardUiState(
                            task = widgetModule.getTaskByIdUseCase().invoke(it.toLong())
                        )
                    } ?: emptyList()
                }

                Column(
                    modifier = GlanceModifier.fillMaxSize()
                        .padding(16.dp)
                        .background(GlanceTheme.colors.surface),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WidgetTitle()
                    Spacer(modifier = GlanceModifier.height(16.dp))
                    LazyColumn {
                        items(widgetTaskCards) {
                            WidgetTaskCard(it)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_TASK_ID = "KEY_TASK_ID"
    }
}