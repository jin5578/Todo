package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults
import com.example.widget.R
import com.example.widget.model.WidgetTaskCardUiState
import com.example.widget.utils.actionStartActivityWithTaskId

@Composable
internal fun WidgetTaskCard(
    uiState: WidgetTaskCardUiState
) {
    val context = LocalContext.current

    Box(
        modifier = GlanceModifier.fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(16.dp)
                .cornerRadius(12.dp)
                .background(GlanceTheme.colors.primary)
                .clickable(
                    actionStartActivityWithTaskId(context, uiState.task.id)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = uiState.task.title,
                style = TextDefaults.defaultTextStyle.copy(
                    fontSize = 16.sp,
                    color = GlanceTheme.colors.onPrimary
                )
            )
            Spacer(modifier = GlanceModifier.height(4.dp))
            if (uiState.task.memo.isNotEmpty()) {
                Text(
                    text = uiState.task.memo,
                    style = TextDefaults.defaultTextStyle.copy(
                        fontSize = 14.sp,
                        color = GlanceTheme.colors.onPrimary
                    )
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
            }
            Row(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ExtraInfo(
                    provider = ImageProvider(R.drawable.svg_clock),
                    title = uiState.task.date.toString()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                ExtraInfo(
                    provider = ImageProvider(R.drawable.svg_calendar),
                    title = uiState.task.date.toString()
                )
            }
        }
    }
}

@Composable
private fun ExtraInfo(
    provider: ImageProvider,
    title: String,
) {
    Row(
        modifier = GlanceModifier
            .background(GlanceTheme.colors.secondaryContainer)
            .cornerRadius(8.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val tint = GlanceTheme.colors.onSecondaryContainer
        Image(
            modifier = GlanceModifier.size(10.dp),
            provider = provider,
            contentDescription = "Calendar Icon",
            colorFilter = ColorFilter.tint(tint)
        )
        Spacer(modifier = GlanceModifier.width(4.dp))
        Text(
            text = title,
            style = TextDefaults.defaultTextStyle.copy(
                fontSize = 10.sp,
                color = GlanceTheme.colors.onSecondaryContainer
            )
        )
    }
}
