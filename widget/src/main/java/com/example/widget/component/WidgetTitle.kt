package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults
import com.example.widget.R
import com.example.widget.utils.actionLaunchIntentForPackage

@Composable
internal fun WidgetTitle() {
    val context = LocalContext.current

    Text(
        modifier = GlanceModifier.clickable(
            actionLaunchIntentForPackage(context)
        ),
        text = context.getString(R.string.app_name),
        style = TextDefaults.defaultTextStyle.copy(
            fontSize = 24.sp,
            color = GlanceTheme.colors.onSurface
        )
    )
}