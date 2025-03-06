package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

/**
 * Copy of [androidx.compose.material3.FloatingActionButton] that supports Brush
 */
@Composable
fun GradientFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    contentColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    elevation: Dp = 3.dp,
    interactionSource: MutableInteractionSource? = null,
    minWidth: Dp = 54.dp,
    minHeight: Dp = 54.dp,
    content: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    GradientSurface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        brush = containerBrush,
        contentColor = contentColor,
        tonalElevation = elevation,
        shadowElevation = elevation,
        interactionSource = interactionSource,
    ) {
        Box(
            modifier = Modifier.defaultMinSize(
                minWidth = minWidth,
                minHeight = minHeight,
            ),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}