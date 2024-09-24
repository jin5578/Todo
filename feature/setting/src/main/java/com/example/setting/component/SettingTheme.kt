package com.example.setting.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.onPrimaryCharcoalBlack
import com.example.design_system.theme.onPrimaryDeepForestGreen
import com.example.design_system.theme.onPrimaryMidnightBlue
import com.example.design_system.theme.onPrimaryMistGray
import com.example.design_system.theme.onPrimarySkyBlue
import com.example.design_system.theme.onPrimarySunRise
import com.example.design_system.theme.onSurfaceMidnightBlue
import com.example.design_system.theme.onSurfaceSunRise
import com.example.design_system.theme.primaryCharcoalBlack
import com.example.design_system.theme.primaryDeepForestGreen
import com.example.design_system.theme.primaryMidnightBlue
import com.example.design_system.theme.primaryMistGray
import com.example.design_system.theme.primarySkyBlue
import com.example.design_system.theme.primarySunRise
import com.example.design_system.theme.surfaceMidnightBlue
import com.example.design_system.theme.surfaceSunRise
import com.example.model.ThemeType
import com.example.setting.R
import com.example.setting.model.ThemeColor

@Composable
internal fun SettingTheme(
    initTheme: ThemeType,
    onSelect: (ThemeType) -> Unit,
) {
    val scrollState = rememberScrollState()

    var selectedTheme by remember { mutableStateOf(initTheme) }

    val isSystemInDarkTheme = isSystemInDarkTheme()

    val themeColors = listOf(
        ThemeColor(
            backgroundColor = if (isSystemInDarkTheme) surfaceMidnightBlue else surfaceSunRise,
            textColor = if (isSystemInDarkTheme) onSurfaceMidnightBlue else onSurfaceSunRise,
            dividerColor = if (isSystemInDarkTheme) primaryMidnightBlue else primarySunRise,
        ),
        ThemeColor(
            backgroundColor = primarySunRise,
            textColor = onPrimarySunRise,
            dividerColor = primarySunRise,
        ),
        ThemeColor(
            backgroundColor = primarySkyBlue,
            textColor = onPrimarySkyBlue,
            dividerColor = primarySkyBlue,
        ),
        ThemeColor(
            backgroundColor = primaryMistGray,
            textColor = onPrimaryMistGray,
            dividerColor = primaryMistGray,
        ),
        ThemeColor(
            backgroundColor = primaryMidnightBlue,
            textColor = onPrimaryMidnightBlue,
            dividerColor = primaryMidnightBlue,
        ),
        ThemeColor(
            backgroundColor = primaryCharcoalBlack,
            textColor = onPrimaryCharcoalBlack,
            dividerColor = primaryCharcoalBlack,
        ),
        ThemeColor(
            backgroundColor = primaryDeepForestGreen,
            textColor = onPrimaryDeepForestGreen,
            dividerColor = primaryDeepForestGreen,
        ),
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            text = stringResource(R.string.choose_theme_style),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeType.entries.forEachIndexed { index, type ->
                ThemeItem(
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
}

@Composable
private fun ThemeItem(
    modifier: Modifier = Modifier,
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
                    horizontal = 24.dp,
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

@Preview
@Composable
private fun SettingThemePreview() {
    TodoTheme {
        SettingTheme(
            initTheme = ThemeType.SUN_RISE,
            onSelect = {}
        )
    }
}