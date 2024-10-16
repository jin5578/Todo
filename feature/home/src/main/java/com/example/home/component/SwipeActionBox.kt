package com.example.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T> SwipeActionBox(
    item: T,
    backgroundColor: Color = MaterialTheme.colorScheme.error,
    icon: ImageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_delete),
    iconTint: Color = MaterialTheme.colorScheme.onError,
    animationDuration: Int = 300,
    onDeleteAction: (T) -> Unit,
    content: @Composable (T) -> Unit
) {
    var isActionDone by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = { swipeToDismissBoxValue ->
            if (swipeToDismissBoxValue == SwipeToDismissBoxValue.EndToStart) {
                isActionDone = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isActionDone) {
        if (isActionDone) {
            delay(animationDuration.toLong())
            onDeleteAction(item)
            state.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    AnimatedVisibility(
        visible = !isActionDone,
        exit = fadeOut(tween(animationDuration))
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                ActionBackground(
                    backgroundColor = backgroundColor,
                    icon = icon,
                    iconTint = iconTint,
                    swipeToDismissBoxState = state
                )
            },
            content = { content(item) },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionBackground(
    backgroundColor: Color,
    icon: ImageVector,
    iconTint: Color,
    swipeToDismissBoxState: SwipeToDismissBoxState,
) {
    var alphaValue = 0f
    var color = Color.Transparent

    if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        color = backgroundColor
        alphaValue = 1.0f
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color, RoundedCornerShape(8.dp))
            .graphicsLayer {
                alpha = alphaValue
            }
            .padding(16.dp), contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )
    }
}