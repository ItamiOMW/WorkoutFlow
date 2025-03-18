package com.itami.workout_flow.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.delete_icon
import workoutflow.composeapp.generated.resources.icon_delete

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    deleteBackgroundColor: Color = WorkoutFlowTheme.colors.dangerColors.danger,
    onDeleteBackgroundColor: Color = WorkoutFlowTheme.colors.dangerColors.onDanger,
    content: @Composable (T) -> Unit,
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        },
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(
                    swipeDismissState = state,
                    backgroundColor = deleteBackgroundColor,
                    onBackgroundColor = onDeleteBackgroundColor
                )
            },
            content = { content(item) },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = true
        )
    }
}

@Composable
private fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState,
    backgroundColor: Color,
    onBackgroundColor: Color,
) {
    val deleteBackgroundColor =
        if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            backgroundColor
        } else {
            Color.Transparent
        }

    val deleteIconColor =
        if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            onBackgroundColor
        } else {
            Color.Transparent
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = deleteBackgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(Res.drawable.icon_delete),
            contentDescription = stringResource(Res.string.delete_icon),
            tint = deleteIconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}