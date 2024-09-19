package com.example.setting.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.DarkBackgroundL1
import com.example.design_system.theme.DarkOn
import com.example.design_system.theme.DarkPrimary
import com.example.design_system.theme.LightBackgroundL1
import com.example.design_system.theme.LightOn
import com.example.design_system.theme.LightPrimary
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.TwilightBackgroundL1
import com.example.design_system.theme.TwilightOn
import com.example.design_system.theme.TwilightPrimary
import com.example.model.ThemeType
import com.example.setting.model.ThemeColor

@Composable
internal fun SettingTheme(
    initTheme: ThemeType,
    onSelect: (ThemeType) -> Unit,
) {
    var selectedTheme by remember { mutableStateOf(initTheme) }

    val themeColors = listOf(
        ThemeColor(
            backgroundColor = LightBackgroundL1,
            textColor = LightOn,
            dividerColor = LightPrimary
        ),
        ThemeColor(
            backgroundColor = LightBackgroundL1,
            textColor = LightOn,
            dividerColor = LightPrimary
        ),
        ThemeColor(
            backgroundColor = TwilightBackgroundL1,
            textColor = TwilightOn,
            dividerColor = TwilightPrimary
        ),
        ThemeColor(
            backgroundColor = DarkBackgroundL1,
            textColor = DarkOn,
            dividerColor = DarkPrimary
        ),
    )

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 30.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ThemeType.entries.forEachIndexed { index, type ->
            ThemeItem(
                modifier = Modifier.weight(1f),
                title = type.themeName,
                themeColor = themeColors[index],
                isSelected = selectedTheme == type,
            ) {
                selectedTheme = type
                onSelect(type)
            }
        }
    }
}

@Composable
private fun ThemeItem(
    modifier: Modifier,
    title: String,
    themeColor: ThemeColor,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .background(themeColor.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = TodoTheme.typography.headlineSmall,
                color = themeColor.textColor,
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 16.dp
                )
            )
        }

        if (isSelected) {
            val animValue = remember {
                Animatable(initialValue = 0f)
            }

            LaunchedEffect(Unit) {
                animValue.animateTo(1f, tween(300))
            }

            Box(
                modifier = Modifier.width(40.dp * animValue.value)
                    .height(4.dp)
                    .background(
                        color = themeColor.dividerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}