package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults
import com.example.widget.model.WidgetTaskCardUiState
import com.example.widget.utils.actionStartActivityWithTaskId

@Composable
internal fun WidgetTaskCard(
    uiState: WidgetTaskCardUiState
) {
    val context = LocalContext.current

    Box(
        modifier = GlanceModifier.fillMaxWidth()
    ) {
        Column(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(16.dp)
                .cornerRadius(12.dp)
                .background(GlanceTheme.colors.tertiaryContainer)
                .clickable(
                    actionStartActivityWithTaskId(context, uiState.task.id)
                )
        ) {
            Text(
                text = uiState.task.title,
                style = TextDefaults.defaultTextStyle.copy(
                    fontSize = 16.sp,
                    color = GlanceTheme.colors.onTertiaryContainer
                )
            )
            /*Row {
                Text(
                    text = uiState.session.toTimeString(),
                    style = TextDefaults.defaultTextStyle.copy(
                        fontSize = 14.sp,
                        color = GlanceTheme.colors.onTertiaryContainer
                    )
                )
                Spacer(modifier = GlanceModifier.width(4.dp))
                Text(
                    uiState.speakerLabel,
                    style = TextDefaults.defaultTextStyle.copy(
                        fontSize = 14.sp,
                        color = GlanceTheme.colors.onTertiaryContainer
                    )
                )
            }*/
        }
    }
}